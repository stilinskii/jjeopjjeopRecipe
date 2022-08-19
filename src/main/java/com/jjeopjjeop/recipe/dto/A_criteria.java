package com.jjeopjjeop.recipe.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.web.util.UriComponentsBuilder;


@ToString
@Setter
@Getter
public class A_criteria {
    private int pageNum; // 사용자가 클릭하는 페이지 번호
    private int amount; // 하나의 페이지에 보여줄 게시물 갯수

    private String type;
    private String keyword;
    private int startNum;



    public A_criteria() {
        this(1, 10);

    } //amount 10으로 바꿔야함

    }


    public A_criteria(int pageNum, int amount) {
        super();
        this.pageNum = pageNum;
        this.amount = amount;
    }


    public String getListLink() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
                .queryParam("pageNum", pageNum)
                .queryParam("amount", amount);
        return builder.toUriString();
    }

    public String[] getTypeArr() {
        return type == null ? new String[]{} : type.split("");
    }

//    public String toString() {
//        return "A_criteria [pageNum=" + pageNum + ", amount=" + amount + ", keyword=" + keyword + "]";
//    }
}


}


