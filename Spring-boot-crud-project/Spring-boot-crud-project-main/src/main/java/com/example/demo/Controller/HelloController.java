package com.example.demo.Controller;

import org.springframework.web.bind.annotation.GetMapping;

public class HelloController {
    @GetMapping("/index")
    public String showHomePage()
    {
        return "index";
    }

}
