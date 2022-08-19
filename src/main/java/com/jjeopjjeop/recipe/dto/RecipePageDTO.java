package com.jjeopjjeop.recipe.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class RecipePageDTO {
    private int currentPage; //현재페이지
    private int commentCurrentPage; // 덧글 현재페이지
    private int totalCount; //총 레코드수
    private int blockCount = 9; // 한 페이지에 보여줄 레코드수.
//    private int blockCount;
    //private int blockPage;
    /* 오류나서 지움.
    private countRequest = 0;
    if(countRequest == 10){
        blockCount = 10;
    }
    아니면 private int blockCount =0;으로 하고 setBlockCount로 설정??
    아니면 private int blockCount;으로 하고 생성자로 하면될듯!! ->해보는 중.
    */


    private int blockPage = 10; // 한 블록에 보여줄 페이지수
    private int totalPage; // 총 페이지수
    private int startRow; // 시작 레코드 번호
    private int endRow; // 끝 레코드 번호
    private int startPage; // 한 블록의 시작 페이지 번호
    private int endPage; // 한 블록의 끝 페이지 번호
    private int number;
    private int rcp_sort; // 정렬 기준

    // 레시피 덧글용
    private int rcp_seq;
    private List<RecipeCommentDTO> recipeCommentDTOList;

    // 레시피 검색용
    private String searchKey;
    private String searchWord;
    private int cate_seq;

    public RecipePageDTO() {
    }

    public RecipePageDTO(int currentPage, int totalCount){
        this.currentPage = currentPage;
        this.totalCount = totalCount;

        // 시작 레코드
        startRow = (currentPage - 1) * blockCount + 1;

        // 끝 레코드
        endRow = startRow + blockCount - 1;

        // 총 페이지 수
        totalPage = totalCount / blockCount + (totalCount % blockCount == 0 ? 0 : 1);

        // 시작 페이지
        startPage = (int)((currentPage - 1) / blockPage) * blockPage + 1;

        // 끝 페이지
        endPage = Math.min(startPage + blockPage - 1, totalPage);
    }

    //임시로 넣음(정아)
  /*  public RecipePageDTO(int blockCount, int blockPage, int currentPage, int totalCount){
        this.blockCount = blockCount;
        this.blockPage = blockPage;
        this.currentPage = currentPage;
        this.totalCount = totalCount;

        // 시작 레코드
        startRow = (currentPage - 1) * blockCount + 1;

        // 끝 레코드
        endRow = startRow + blockCount - 1;

        // 총 페이지 수
        totalPage = totalCount / blockCount + (totalCount % blockCount == 0 ? 0 : 1);

        // 시작 페이지
        startPage = (int)((currentPage - 1) / blockPage) * blockPage + 1;

        // 끝 페이지
        endPage = Math.min(startPage + blockPage - 1, totalPage);
    }

   */

    public RecipePageDTO(int commentCurrentPage, int totalCount, int rcp_seq){
        this.commentCurrentPage = commentCurrentPage;
        this.totalCount = totalCount;
        this.rcp_seq = rcp_seq;
        this.blockCount = 5;
        this.blockPage = 3;

        // 시작 레코드
        startRow = (commentCurrentPage - 1) * blockCount + 1;

        // 끝 레코드
        endRow = startRow + blockCount - 1;

        // 총 페이지 수
        totalPage = totalCount / blockCount + (totalCount % blockCount == 0 ? 0 : 1);

        // 시작 페이지
        startPage = (int)((commentCurrentPage - 1) / blockPage) * blockPage + 1;

        // 끝 페이지
        endPage = Math.min(startPage + blockPage - 1, totalPage);
    }

    public RecipePageDTO(int currentPage, int totalCount, String searchKey) {
        this(currentPage, totalCount);
        this.searchKey = searchKey;
    }
}
