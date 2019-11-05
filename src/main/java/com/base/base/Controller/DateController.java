package com.base.base.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateController {
    // 날짜 잡아주기
    Date datetime2 = new Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM월 dd일 HH시 mm분 ss분", Locale.KOREA);
//    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
    String createdDate1 = simpleDateFormat.format(datetime2);
//    String createdDate2 = simpleDateFormat1.format(datetime2);
}
