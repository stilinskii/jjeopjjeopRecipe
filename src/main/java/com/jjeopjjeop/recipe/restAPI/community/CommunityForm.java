package com.jjeopjjeop.recipe.restAPI.community;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CommunityForm {

    @ApiModelProperty(example = "유저아이디")
    private String user_id;

    @ApiModelProperty(example = "0 - 자유글 , 1 - 레시피후기")
    private String category;

    @ApiModelProperty(example = "레시피 후기글일경우 0보다 큰 숫자(레시피 일련번호)입력")
    private Integer rcp_seq;//레시피후기일경우 번호

    @ApiModelProperty(example = "제목")
    private String title;

    @ApiModelProperty(example = "내용")
    private String content;

}
