package com.cafecostes.cafe.Controller;

import com.cafecostes.cafe.AES256.AES256Cipher;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Log
@Controller    // This means that this class is a Controller
@RequestMapping(path="/test") // This means URL's start with /demo (after Application path)
public class TestController {
    String key = "11111222223333344444555556678";    //key는 16~32자리
    @GetMapping("encrypt")
    public @ResponseBody
    String encrypt(@RequestParam String str) throws Exception {
        //Encoder encoder = new Encoder();
        //str = encoder.encoding(str);
        return AES256Cipher.AES_Encode(str);
        // return AES256Cipher.AES_Encode(str);
    }

    @GetMapping("decrypt")
    public @ResponseBody
    String decrypt(@RequestParam String str) throws Exception {
        //Encoder encoder = new Encoder();
        //str = encoder.encoding(str);
        return "푸는건 안돼요~";
        //return AES256Cipher.AES_Decode(str);
    }

}
