package com.jjeopjjeop.recipe.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class PageDTO {// 페이지 관련 정보들


    // 페이지를 처리하는데에 필요한 것은 현재페이지와 전체 레코드 수

    private int currentPage; // 현재페이지
    private int totalCount; // 총 레코드 수
    private int blockCount = 9;// 한 페이지에 보여줄 레코드 수
    private int blockPage = 4; // 한 블록에 보여줄 페이지수
    private int totalPage; // 총 페이지수
    private int startRow;// 시작 레코드 번호
    private int endRow; // 끝 레코드 번호
    private int startPage;// 한 블록의 시작 페이지 번호
    private int endPage; // 한 블록의 끝페이지 번호
    private int number;

    private String searchKey;
    private String searchWord;

    public PageDTO() {
    }

    // 아래같은 이런 계산은 외우는거아니고 공식을 사용하는것. 위에꺼말고 다른공식 써도 상관없음
    public PageDTO(int currentPage, int totalCount) {
        this.currentPage = currentPage;
        this.totalCount = totalCount;

        // 시작레코드
        startRow = (currentPage - 1) * blockCount + 1;

        // 끝레코드
        endRow = startRow + blockCount - 1;

        // 총페이지수
        totalPage = totalCount / blockCount + (totalCount % blockCount == 0 ? 0 : 1);

        // 시작페이지
        startPage = (int) ((currentPage - 1) / blockPage) * blockPage + 1; // 현재페이지가 1이면 0이 되니까 +1해줌

        // 끝페이지
        endPage = startPage + blockPage - 1;
        if (totalPage < endPage)
            endPage = totalPage;

        // 리스트페이지에 출력번호
        number = totalCount - (currentPage - 1) * blockCount;//->이건 그냥 계산만 해두고 우리가 쓰지는 않습니다.
    }
}