package com.cafecostes.cafe.Controller;

import com.cafecostes.cafe.repository.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Log
@Controller
public class WebController {



    @Autowired
    private UsersRepository usersRepository;

    @RequestMapping(value="/", method = {RequestMethod.GET, RequestMethod.POST})
    public String main() {
        return "index.html";
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting.html";
    }



}
