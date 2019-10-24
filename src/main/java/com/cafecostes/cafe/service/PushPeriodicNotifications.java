package com.cafecostes.cafe.service;

import com.cafecostes.cafe.formatter.Encoder;
import com.cafecostes.cafe.repository.UsersRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Log
@Controller
@Setter
@Getter
public class PushPeriodicNotifications {

    @Autowired
    UsersRepository usersRepository;

    public static String tokens[] = new String[1000];
    // 파라미터로 String Array로 토큰 실어주면 될듯
    public static String PeriodicNotificationJson() throws JSONException {
        LocalDate localDate = LocalDate.now();
        List<String> sampleData = new ArrayList<>();
        for (int i = 0; i < PushPeriodicNotifications.tokens.length; i++){
            try{
                if ( PushPeriodicNotifications.tokens[i].equals(" ")){
                }
                else {
                    sampleData.add(PushPeriodicNotifications.tokens[i]);
                }
            } catch (NullPointerException e){
            }
        }
        JSONObject body = new JSONObject();
        List<String> tokenlist = new ArrayList<String>();
        for(int i=0; i<sampleData.size(); i++){
            tokenlist.add(sampleData.get(i));
        }
        JSONArray array = new JSONArray();
        for(int i=0; i<tokenlist.size(); i++) {
            array.put(tokenlist.get(i));
        }
        body.put("registration_ids", array);
        JSONObject notification = new JSONObject();

        notification.put("title","title");
        notification.put("body","Today is "+localDate.getDayOfWeek().name()+"!");
        body.put("notification", notification);
        System.out.println(body.toString());
        return body.toString();
    }


    // 푸시 메시지를 조합해주는 곳
    public static String SuccessOrder(String userName, String title, String content , String token) throws JSONException {
        LocalDate localDate = LocalDate.now();
        List<String> sampleData = new ArrayList<>();//
        sampleData.add(token);
        JSONObject body = new JSONObject();
        List<String> tokenlist = new ArrayList<String>();
        log.info("샘플데이타 길이 : " + sampleData.size());
        for(int i=0; i<sampleData.size(); i++){
            tokenlist.add(sampleData.get(i));
        }
        log.info("토큰리스트 : " + tokenlist.toString());
        JSONArray array = new JSONArray();
        for(int i=0; i<tokenlist.size(); i++) {
            array.put(tokenlist.get(i));
        }
        log.info(array.toString());
        body.put("registration_ids", array);
        JSONObject notification = new JSONObject();

        String originalStr = "costes 주문!"; // 테스트
        Encoder encoder = new Encoder();
        originalStr = encoder.encoding2(originalStr);

        content = encoder.encoding2(content);

        notification.put("title", userName + "님! "+ originalStr + title);
        notification.put("body", content);
        notification.put("sound", "default");
        body.put("notification", notification);
        System.out.println( "이 내용으로 푸시 쏜다~? " + body.toString());
        return body.toString();
    }
}
