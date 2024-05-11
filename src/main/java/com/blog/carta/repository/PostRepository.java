package com.blog.carta.repository;

import com.blog.carta.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository như là một phần của ORM (Object-Relational Mapping), tương tự như cách bạn sử dụng Sequelize trong Node.js
// giúp tương tác với cơ sở dữ liệu một cách dễ dàng hơn bằng cách sử dụng các đối tượng
// và phương thức của chúng thay vì viết các truy vấn SQL thủ công

// không cần add annotation @Repository tại vì đó đã được extends
// org.springframework.data.jpa.repository.support.SimpleJpaRepository
// khi kế thừa JpaRepository thì sẽ có toàn bộ phương thức curd cho Post entity
public interface PostRepository extends JpaRepository<Post, Long> {
}
