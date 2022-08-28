package com.jjeopjjeop.recipe.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@Getter
@Setter
@NoArgsConstructor
public class CommunityDTO {

    private Integer id;
    private String user_id;
    private Integer rcp_seq;//레시피후기일경우 번호
    private String category;

    @Size(min=1,max=30)
    private String title;
    @Size(min=1)
    private String content;

    private Date created_at;
    private String formatted_created_at;
    private Date updated_at;
    private Integer read_count;
    private Integer like_count;
    private Integer report;
    private Integer image_exists;//1이 true

    //transient
    private List<ImageDTO> images;
    private boolean isLiked;

    public CommunityDTO(String user_id, String category,Integer rcp_seq, String title, String content) {
        this.user_id = user_id;
        this.category = category;
        this.rcp_seq = rcp_seq;
        this.title = title;
        this.content = content;
    }

    public CommunityDTO(Integer id,String user_id, String category,Integer rcp_seq, String title, String content) {
        this.id=id;
        this.user_id = user_id;
        this.category = category;
        this.rcp_seq = rcp_seq;
        this.title = title;
        this.content = content;
    }

    public String getFormatted_created_at() {
        //오늘이면 시간만 넣고 아니면 날짜만 넣기
        SimpleDateFormat simpleDateFormatForPast = new SimpleDateFormat("yyyy.MM.dd");
        SimpleDateFormat simpleDateFormatForToday = new SimpleDateFormat("HH:mm");

        String created_at_str=simpleDateFormatForPast.format(created_at);
        String today=simpleDateFormatForPast.format(new Date());

        return created_at_str.equals(today) ? simpleDateFormatForToday.format(created_at):simpleDateFormatForPast.format(created_at);
    }
}
