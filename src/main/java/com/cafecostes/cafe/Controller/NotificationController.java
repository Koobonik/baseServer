package com.cafecostes.cafe.Controller;

import com.cafecostes.cafe.DB.Users;
import com.cafecostes.cafe.repository.UsersRepository;
import com.cafecostes.cafe.service.PushNotificationService;
import com.cafecostes.cafe.service.PushPeriodicNotifications;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Log
@RestController
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자 자동 추가 // 기본 생성자의 접근 권한을 protected로 제한
public class NotificationController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PushNotificationService pushNotificationService;

    @Autowired
    private UsersRepository usersRepository;


    // 세션 담아주고 파라미터도 담아줘야 함
    @GetMapping(value = "/send")
    public @ResponseBody ResponseEntity<String> send(HttpSession httpSession) throws JSONException, InterruptedException  {


        // 관리자 세션일 때만 작동하게
        if (httpSession.getAttribute("name").equals("관리자")){

            //
        Iterable<Users> users = usersRepository.findAll();
        for(int i = 0; i < ((List<Users>) users).size(); i++){
            try {

                PushPeriodicNotifications.tokens[i] = ((List<Users>) users).get(i).getFirebaseToken();
                log.info(PushPeriodicNotifications.tokens[i]);
            }
            catch (NullPointerException e){
            }
        }
            String notifications = PushPeriodicNotifications.PeriodicNotificationJson();

            HttpEntity<String> request = new HttpEntity<>(notifications);

            CompletableFuture<String> pushNotification = pushNotificationService.send(request);
            CompletableFuture.allOf(pushNotification).join();

            try{
                String firebaseResponse = pushNotification.get();
                return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
            }
            catch (InterruptedException e){
                logger.debug("got interrupted!");
                throw new InterruptedException();
            }
            catch (ExecutionException e){
                logger.debug("execution error!");
            }

            return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);

        }
        return null;
    }

    // 주문한 소비자에게 보내는 것.
    // 푸시를 눌렀을 때 주문 내역으로 이동하는 url? 값도 첨부를 해줘야 함
    @GetMapping(value = "/sendToCustomer")
    public ResponseEntity<String> sendToCustomer(@RequestParam String userName, @RequestParam String token) throws JSONException, InterruptedException  {
        //Users users = usersRepository.findByUserName(userName);
        //https://cafecostes.com/sendToCustomer?userName=구본익&token=fj9B7QconG0:APA91bEbxBRFISLnUgxPkojSecov4L2vkd46bUvld_c7q__DZvO-aGeCD9U4TZ9N8ct1g_qWEheusw-JHn-CDNS4_62TbXgA3OnBLXiPPfQqVK9n8Lm0yaApGxywCPuYlCxmsbIyFMmR
        log.info(userName);
        log.info(token);
        String notifications = PushPeriodicNotifications.SuccessOrder(userName, "주문이 완료되었습니다.", "매장 상황에 따라 시간이 더 소요될 수 있습니다.", token);
        log.info(notifications);
        HttpEntity<String> request = new HttpEntity<>(notifications);

        CompletableFuture<String> pushNotification = pushNotificationService.send(request);
        CompletableFuture.allOf(pushNotification).join();

        try{
            String firebaseResponse = pushNotification.get();
            log.info("파이어베이스 리스폰 "+ firebaseResponse);
            return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
        }
        catch (InterruptedException e){
            logger.debug("got interrupted!");
            throw new InterruptedException();
        }
        catch (ExecutionException e){
            logger.debug("execution error!");
        }

        return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);

    }
}