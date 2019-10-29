package com.base.base.firebase;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Service
public class PushNotificationService {
    private static final String firebase_server_key="AAAAZ-txOqM:APA91bGtcF68z1hWcRzJ-nuvvvmU0uciBLxpTj_CW05GErROfmRlKrDbh3UKcNCQelQzm2SIMI9alW7b3_YIMCVltAM0nl2uZQ2AkUrOtUFrVb-7qMOBJVRy8rWH0BCN9Z353tF1iwal";
    private static final String firebase_api_url="https://fcm.googleapis.com/fcm/send";

    @Async
    public CompletableFuture<String> send(HttpEntity<String> entity) {

        RestTemplate restTemplate = new RestTemplate();

        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();

        interceptors.add(new HeaderRequestInterceptor("Authorization",  "key=" + firebase_server_key));
        interceptors.add(new HeaderRequestInterceptor("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE));
//        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json;"));
        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json; charset=UTF-8"));
        restTemplate.setInterceptors(interceptors);

        String firebaseResponse = restTemplate.postForObject(firebase_api_url, entity, String.class);

        return CompletableFuture.completedFuture(firebaseResponse);
    }
}
