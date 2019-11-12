package com.base.base.firebase;

import com.base.base.Controller.DateController;
import com.base.base.DB.LogHistory;
import com.base.base.repository.FirebaseTokenRepository;
import com.base.base.repository.LogHistoryRepository;
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
    private FirebaseTokenRepository firebaseTokenRepository;

    @Autowired
    private LogHistoryRepository logHistoryRepository;

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
                    sampleData.add(PushPeriodicNotifications.tokens[i]);// = PushPeriodicNotifications.tokens[i];
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

        DateController dateController = new DateController();

        notification.put("title", dateController.getCreatedDate2());
        // notification.put("title","Warning");
        String bodyStr = "We got a some warning";
        notification.put("body", bodyStr);
        // notification.put("sound", "default"); // 소리나 진동 울리게 하고 싶으면 이거 주석 해제
        body.put("notification", notification);
        System.out.println(body.toString());

        LogHistory logHistory = new LogHistory(dateController.getCreatedDate2(), bodyStr);
        logHistory.setDate(dateController.getCreatedDate2());
        logHistory.setAccess(bodyStr);
        System.out.println(logHistory.getDate() + " " + logHistory.getAccess());
        return body.toString()+":::" + dateController.getCreatedDate2() + ":::" + "푸시 알림";
    }

    // 푸시 메시지를 조합해주는 곳
    public static String sendPush(String userName, String title, String content , String token) throws JSONException {
        LocalDate localDate = LocalDate.now();
        List<String> sampleData = new ArrayList<>();//{"fj9B7QconG0:APA91bEbxBRFISLnUgxPkojSecov4L2vkd46bUvld_c7q__DZvO-aGeCD9U4TZ9N8ct1g_qWEheusw-JHn-CDNS4_62TbXgA3OnBLXiPPfQqVK9n8Lm0yaApGxywCPuYlCxmsbIyFMmR","dzN-7eJCwLM:APA91bHtxO911yQZJAA5TB9CecQRKBmXA8D_eZPGqaRQCC-N1NljeESSPsMFLC9T3B9u__TbO4AxhFliCTscnvlyNkgyRy5tpB2iz6qd-rgsbMMdQRzVmczMfBkEeKtcu9dtH0eGBunG","device token value 3"};
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

        notification.put("title", userName + "님! "+ originalStr + title);
        notification.put("body", content);
        notification.put("sound", "default");
        body.put("notification", notification);
        System.out.println( "이 내용으로 푸시 쏜다~? " + body.toString());
        return body.toString();
    }
}
