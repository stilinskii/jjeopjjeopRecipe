package com.jjeopjjeop.recipe.pagenation;

import com.jjeopjjeop.recipe.dto.PagenationDTO;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pagenation {
    private Integer page;//해당페이지
    private Integer totalPageCnt;//총 페이지 개수
    private Integer perPage;//페이지에 보여줄 글 수.
    private Integer count;//전체 글개수

    private Integer startRow;//DB에서 가져올때
    private Integer endRow;

    private Integer startPageNum;//프론트에 보여질 페이지네이션 시작 번호
    private Integer endPageNum;//끝번호

    //heeyeon
    private String keyword;

    // ↓레시피용으로 추가↓
    private Integer blockPageCnt;//한 블록에 보여줄 페이지수

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

    // ↓레시피, 레시피 댓글용↓
    public Pagenation(int page, int count, boolean rcpOrCo){
        // rcpOrCo가 true일 시 레시피, false일 시 레시피 댓글
        this.page = page;
        this.count = count;
        this.perPage = rcpOrCo ? 9 : 5;
        this.blockPageCnt = rcpOrCo ? 10 : 3;

        // 시작 레코드
        startRow = page != 0 ? (page - 1) * perPage + 1 : 1;

        // 끝 레코드
        endRow = startRow + perPage - 1;

        // 총 페이지 수
        totalPageCnt = count > 0 ? count / perPage + (count % perPage == 0 ? 0 : 1) : 1;

        // 시작 페이지
        startPageNum = (int)((page - 1) / blockPageCnt) * blockPageCnt + 1;

        // 끝 페이지
        endPageNum = Math.min(startPageNum + blockPageCnt - 1, totalPageCnt);
    }
}
