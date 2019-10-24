package com.cafecostes.cafe;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.io.IOException;
import java.nio.charset.Charset;


@SuppressWarnings("ALL")
@SpringBootApplication
public class BaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class, args);

//        FirebaseOptions options = null;
//        try {
//            options = new FirebaseOptions.Builder()
//                    .setCredentials(GoogleCredentials.getApplicationDefault())
//                    .setDatabaseUrl("https://cafe-costes.firebaseio.com/")
//                    .build();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        FirebaseApp.initializeApp(options);
//        FirebaseMessagingSnippets firebaseMessagingSnippets = new FirebaseMessagingSnippets();
//        try {
//            firebaseMessagingSnippets.sendToToken();
//        } catch (FirebaseMessagingException e) {
//            e.printStackTrace();
//        }
    }


    @Bean
    CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
    }


//    @Bean
//    CommandLineRunner init(StorageService storageService) {
//        this.storageService = storageService;
//        return (args) -> {
//            storageService.deleteAll();
//            storageService.init();
//        };
//    }
}