package com.jjeopjjeop.recipe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagenationDTO {
    private Integer page;//해당페이지
    private Integer totalPageCnt;//총 페이지 개수
    private Integer perPage;//페이지에 보여줄 글 수.
    private Integer count;//전체 글개수

    private Integer startRow;//DB에서 가져올때
    private Integer endRow;

    private Integer startPageNum;//프론트에 보여질 페이지네이션 시작 번호
    private Integer endPageNum;//끝번호

}
