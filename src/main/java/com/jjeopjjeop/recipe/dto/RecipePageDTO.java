package com.jjeopjjeop.recipe.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class RecipePageDTO {
    private int currentPage; //현재페이지
    private int totalCount; //총 레코드수
    private int blockCount = 9; // 한 페이지에 보여줄 레코드수
    private int blockPage = 10; // 한 블록에 보여줄 페이지수
    private int totalPage; // 총 페이지수
    private int startRow; // 시작 레코드 번호
    private int endRow; // 끝 레코드 번호
    private int startPage; // 한 블록의 시작 페이지 번호
    private int endPage; // 한 블록의 끝 페이지 번호
    private int number;

    private String searchKey;
    private String searchWord;

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

    public RecipePageDTO(int currentPage, int totalCount, String searchKey) {
        this(currentPage, totalCount);
        this.searchKey = searchKey;
    }
}
