package com.blog.carta.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseGetListObjectResponse {
    private long totalElements;
    private int totalPages;
    private int pageNumber;
    private int pageSize;
    private boolean isLastPage;
}
