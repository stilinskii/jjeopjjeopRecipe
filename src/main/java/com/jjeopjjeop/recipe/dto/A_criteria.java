package com.jjeopjjeop.recipe.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class A_criteria {
    private int pageNum; // 사용자가 클릭하는 페이지 번호
    private int amount; // 하나의 페이지에 보여줄 게시물 갯수
    private int startNum;


    public A_criteria() {
        this(1, 10);
    }

    public A_criteria(int pageNum, int amount) {
        super();
        this.pageNum = pageNum;
        this.amount = amount;
    }


}

