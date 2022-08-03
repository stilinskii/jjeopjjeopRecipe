package com.jjeopjjeop.recipe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagenationDTO {
    private Integer page;//해당페이지
    private Integer totalPageCnt;//총 페이지 개수
    private Integer perPage = 10;//페이지에 보여줄 글 수.
    private Integer count;//전체 글개수

    private Integer startPage;
    private Integer endPage;

}
