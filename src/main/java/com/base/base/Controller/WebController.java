package com.base.base.Controller;

import com.base.base.firebase.PushNotificationService;
import com.base.base.firebase.PushPeriodicNotifications;
import com.base.base.repository.UsersRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;


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

        String notifications = PushPeriodicNotifications.sendPush("hihi", "Thank you!", "Please wait 5 miniute", "ㅁㄴㅇ");
        log.info(notifications);
        HttpEntity<String> request = new HttpEntity<>(notifications);

        CompletableFuture<String> pushNotification = pushNotificationService.send(request);
        CompletableFuture.allOf(pushNotification).join();
        return "";
    }





}
