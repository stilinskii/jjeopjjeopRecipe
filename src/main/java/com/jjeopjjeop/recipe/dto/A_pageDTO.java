package com.jjeopjjeop.recipe.dto;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@ToString
public class A_pageDTO {// 페이지 관련 정보들


    // 페이지를 처리하는데에 필요한 것은 현재페이지와 전체 레코드 수

    private int startPage; // 페이징 시작번호
    private int endPage; // 페이징 끝 번호
    private int total; // 전체 데이터 수
    private A_criteria cri;
    private boolean prev,next; // 이전 , 다음 버튼 유무


    // 아래같은 이런 계산은 외우는거아니고 공식을 사용하는것. 위에꺼말고 다른공식 써도 상관없음
    public A_pageDTO(A_criteria cri, int total) {
        this.cri = cri;
        this.total = total;

        this.endPage = (int)(Math.ceil(cri.getPageNum()/10.0))*10;
        this.startPage = this.endPage-9;
        int realEnd = (int)(Math.ceil((total*1.0)/cri.getAmount()));
        if(realEnd<this.endPage) {
            this.endPage=realEnd;
        }
        this.prev = this.startPage>1;
        this.next = this.endPage<realEnd;
    }


}