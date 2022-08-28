package com.jjeopjjeop.recipe.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.InitBinder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Data
public class CommunitySearchForm {
    private String period;
    private String category;
    private String option;
    private String keyword;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date from;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date to;


    //날짜가 해당날의 정각으로 들어오기때문에 from은 from보다 큰 수를 구하는거라 상관없지만
    //to는 해당날이랑 같거나 작은날을 찾는거기떄문에 해당날의 23시59분59초 보다 같거나 작은 데이터를 가져와야함.
    //여기서 해줘야 프론트에 영향이 안감.
    public Date getTo() {
        if(to==null){
            return to;
        }else{
            Calendar cal = Calendar.getInstance();
            cal.setTime(to);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            return new Date(cal.getTimeInMillis());
        }
    }
}
