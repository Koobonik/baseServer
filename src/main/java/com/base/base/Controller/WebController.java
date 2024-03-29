package com.base.base.Controller;

import com.base.base.DB.LogHistory;
import com.base.base.DB.Parameter;
import com.base.base.DB.FirebaseToken;
import com.base.base.firebase.PushNotificationService;
import com.base.base.firebase.PushPeriodicNotifications;
import com.base.base.repository.FirebaseTokenRepository;
import com.base.base.repository.LogHistoryRepository;
import lombok.extern.java.Log;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;


@Log
@Controller
public class WebController {

    @Autowired
    PushNotificationService pushNotificationService;

    @Autowired
    private FirebaseTokenRepository firebaseTokenRepository;

    @Autowired
    private LogHistoryRepository logHistoryRepository;

    List<Integer> distance = new ArrayList<Integer>();


    int i = 0;
    int count = 0;

    private static boolean status = true;
    private static String str = "hi";
    @RequestMapping(value = "func", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    public @ResponseBody String func(@RequestBody Parameter parameter){
        return "false";
    }

    @RequestMapping(value="/", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody String main() {
        return "index.html";
    }

    @GetMapping("/greeting")
    public @ResponseBody String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting.html";
    }

    @GetMapping("/push")
    public @ResponseBody String push(@RequestParam(name="name", required=false, defaultValue="World") String name) {
        if(name == null){
            name = "dXh_GrFyKUM:APA91bGcgYH2VPAPGz8TT16rDIpbNu_nsm9OATdu_widX7lRn6KvoPE7n74JTu_wUTDfX_hi9qN5ln9bPz5csJu2196Pi5M2MhfQPudwpIjoYR1IuUYsmFEUBT3Sy3s_XtVo2jTOoUo5";
        }
        String notifications = PushPeriodicNotifications.sendPush("hihi", "pushtest you!", "Please wait 5 miniute", name);
        log.info(notifications);
        HttpEntity<String> request = new HttpEntity<>(notifications);

        CompletableFuture<String> pushNotification = pushNotificationService.send(request);
        CompletableFuture.allOf(pushNotification).join();
        return "";
    }

    @GetMapping("/pushAll")
    public @ResponseBody ResponseEntity<String> pushAll() throws JSONException, InterruptedException {
        if(WebController.status){
            Iterable<FirebaseToken> firebaseTokens = firebaseTokenRepository.findAll();
            for(int i = 0; i < ((List<FirebaseToken>) firebaseTokens).size(); i++){
                try {

                    PushPeriodicNotifications.tokens[i] = ((List<FirebaseToken>) firebaseTokens).get(i).getFirebaseToken();
                    log.info(PushPeriodicNotifications.tokens[i]);
                }
                catch (NullPointerException e){
                }
            }
            // split(":::") 해줘야함
            String notifications = PushPeriodicNotifications.PeriodicNotificationJson();
            String[] noti = notifications.split(":::");

            HttpEntity<String> request = new HttpEntity<>(noti[0]);
            System.out.println(request);

            CompletableFuture<String> pushNotification = pushNotificationService.send(request);
            CompletableFuture.allOf(pushNotification).join();

            try{
                String firebaseResponse = pushNotification.get();

                // 푸시를 성공적으로 보냈다면 스위치 off
                WebController.status = false;

                LogHistory logHistory = new LogHistory();
                logHistory.setDate(noti[1]);
                logHistory.setAccess(noti[2]);

                logHistoryRepository.save(logHistory);


                return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
            }
            catch (InterruptedException e){
                throw new InterruptedException();
            }
            catch (ExecutionException e){
            }

            return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Status : false", HttpStatus.BAD_REQUEST);

    }

    // 유저가 앱을 키면 자동으로데 파이어베이스 토큰을 주는데 그 토큰을 등록하는 소스코드
    @RequestMapping(value = "registerFirebaseToken", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    public @ResponseBody String registerFirebaseToken(@RequestBody Parameter parameter){
        FirebaseToken firebaseToken = firebaseTokenRepository.findByFirebaseToken(parameter.getData1());
        System.out.println("뭐가 문제지 " + parameter.getData1());
        // 값이있으면 이미 등록되어 있음
        if(firebaseToken == null){ // 비어있는 값이면 등록 해줘야함
            System.out.println("토큰 set " + parameter.getData1());
            FirebaseToken firebaseToken2 = new FirebaseToken(parameter.getData1());
            firebaseTokenRepository.save(firebaseToken2);
            System.out.println("성공적으로 토큰 저장");
            return "true";
        }
        return "false";
    }


    // 푸시알림 쏠지 말지 상태값 바꾸어주는 메소드
    @RequestMapping(value = "ChangeStatus", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    public @ResponseBody String ChangeStatus(@RequestBody Parameter parameter){
        System.out.println("ChangeStatus 들어옴");
        // true 일때
        if(parameter.getData1().equals("true")){

            WebController.status = true;
        }
        else {
            WebController.status = false;
        }
        System.out.println(WebController.status + " 반환");
        return WebController.status + "";
    }
    @RequestMapping(value = "getLogs", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    public @ResponseBody
    Iterable<LogHistory> getLogs(){
        Iterable<LogHistory> logHistories = logHistoryRepository.findAllDesc();
        return logHistories;
    }

    // 거리에 담겨있던 변수들을 초기화 해주는 메소드
    @RequestMapping(value = "setLock", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    public @ResponseBody String setLock(){
        distance.clear();
        return "true";
    }

    @RequestMapping(value = "getStatus", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    public @ResponseBody String getStatus() {
        return WebController.status+"";
    }

    // 앱에서 지속적으로 보내오는 데이터들
    @GetMapping("/setTest")
    public @ResponseBody String setTest(@RequestParam String test){
        System.out.println(test);
        int a = Integer.parseInt(test);

        distance.add(a);
        System.out.println(distance);
        System.out.println(distance.iterator().hasNext());

        // 잠금을 걸어야 작동함.
        if(WebController.status){
            i += a;
            count++;
            // 충분한 데이터를 모으기 위해서 카운트 10
            if(count > 10){
                System.out.println("평균 값 : " + i/count);
                System.out.println("평균 값 90% : " + i/count * 0.8);
                System.out.println("평균 값 110% : " + i/count * 1.2);
                System.out.println("지금 들어온 값 : " + test);
                if( Integer.parseInt(test) < i/count * 0.8 || i/count*1.2 < Integer.parseInt(test)){
                    try {
                        i = 0;
                        count = 0;
                        distance.clear();
                        pushAll();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else {
            i = 0;
            count = 0;
            distance.clear();
        }



//        if(Integer.parseInt(test) )
        WebController.str = test;
        return distance.toString();
    }
    @GetMapping("/getTest")
    public @ResponseBody String getTest(){
        return distance.toString();
    }
}
