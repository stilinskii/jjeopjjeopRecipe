package com.jjeopjjeop.recipe.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Getter @Setter
public class CommunityCommentDTO {

    private Integer id;
    private Integer board_id;
    private String user_id;
    @NotNull(message = "내용을 입력해주세요")
    private String content;
    private Date created_at;
    private Date updated_at;
    private Integer report;

    private String formatted_created_at;

    public String getFormatted_created_at() {
        //오늘이면 시간만 넣고 아니면 날짜만 넣기
        SimpleDateFormat simpleDateFormatForPast = new SimpleDateFormat("yyyy.MM.dd");
        SimpleDateFormat simpleDateFormatForToday = new SimpleDateFormat("HH:mm");

        String created_at_str=simpleDateFormatForPast.format(created_at);
        String today=simpleDateFormatForPast.format(new Date());

        return created_at_str.equals(today) ? simpleDateFormatForToday.format(created_at):simpleDateFormatForPast.format(created_at);
    }
}
