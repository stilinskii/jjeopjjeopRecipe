package com.jjeopjjeop.recipe.pagenation;

import com.jjeopjjeop.recipe.dto.PagenationDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Pagenation {
    private Integer page;//해당페이지
    private Integer totalPageCnt;//총 페이지 개수
    private Integer perPage;//페이지에 보여줄 글 수.
    private Integer count;//전체 글개수

    private Integer startRow;//DB에서 가져올때
    private Integer endRow;

    private Integer startPageNum;//프론트에 보여질 페이지네이션 시작 번호
    private Integer endPageNum;//끝번호

    public Pagenation(Integer page, Integer perPage, Integer recordCount) {

        int startRow = (page != null) ? page : 0; // page가 입력되지 않았으면 자동으로 1페이지로(0이 1)
        int count = recordCount;// 전체 글 개수

        int totalPageCnt = (int) Math.ceil((double) count/(double) perPage); // 전체글 / 페이지에 보여질 개수 = 페이지 수
        int startPageNum1 = page >=totalPageCnt-3 ? totalPageCnt-4:Math.max(1, page -1);
        int startPageNum = startPageNum1 <= 0 ? 1:startPageNum1;  //totalPageCnt가 4이하면 startPageNum1이 0이하로 나오니까 1로 고쳐주기.
        int endPageNum = page >=totalPageCnt-3 ? totalPageCnt: Math.min(startPageNum + 4, totalPageCnt);

        this.perPage = perPage;
        this.count = count;
        this.page = startRow;
        this.totalPageCnt = totalPageCnt;
        this.startRow = 1 + (perPage * page);
        this.endRow = perPage*(page +1);
        this.startPageNum = startPageNum;
        this.endPageNum = endPageNum;
    }


}
