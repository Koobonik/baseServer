package com.base.base.Controller;

import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Getter
public class DateController {
    // 날짜 잡아주기
    private Date datetime2 = new Date();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM월 dd일 HH시 mm분 ss분", Locale.KOREA);
    private SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
    private String createdDate1 = simpleDateFormat.format(datetime2);
    private String createdDate2 = simpleDateFormat1.format(datetime2);
    public void DateController(){

    }
//    String createdDate2 = simpleDateFormat1.format(datetime2);
}
