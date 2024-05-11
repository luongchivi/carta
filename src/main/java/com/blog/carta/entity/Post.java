package com.blog.carta.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data // dùng để tạo getter/setter cho các field
@AllArgsConstructor // tạo 1 constructor với full field
@NoArgsConstructor // tạo 1 constructor với không có field nào hết

@Entity // dùng annotation Entity
@Table( // setup tên bảng cho Class POST là posts và column title unique
        name = "posts", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
)
public class Post {
    @Id // add primary key cho posts table
    @GeneratedValue(
            // @GeneratedValue là Annotation này chỉ định cách sinh giá trị cho trường id
            // strategy = GenerationType.IDENTITY có nghĩa là tự động tăng trường id
            // còn rất nhiều thứ khác nữa ví dụ như UUID, AUTO, SEQUENCE, TABLE
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "content", nullable = false)
    private String content;

    @CreationTimestamp
    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL, // có nghĩa là khi delete bài post thì sẽ tự động xóa luôn tất cả comments con của bài post
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<Comment> comments = new HashSet<>();
}
