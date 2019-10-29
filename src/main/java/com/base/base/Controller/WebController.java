package com.base.base.Controller;

import com.base.base.DB.Users;
import com.base.base.firebase.PushNotificationService;
import com.base.base.firebase.PushPeriodicNotifications;
import com.base.base.repository.UsersRepository;
import lombok.extern.java.Log;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Log
@Controller
public class WebController {

    @Autowired
    PushNotificationService pushNotificationService;

    @Autowired
    private UsersRepository usersRepository;

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
        System.out.println(request);

        CompletableFuture<String> pushNotification = pushNotificationService.send(request);
        CompletableFuture.allOf(pushNotification).join();

        try{
            String firebaseResponse = pushNotification.get();
            return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
        }
        catch (InterruptedException e){
            throw new InterruptedException();
        }
        catch (ExecutionException e){
        }

        return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
    }





}
