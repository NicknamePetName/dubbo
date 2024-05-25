package com.youkeda.exercise.demo.api.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @ResponseBody
    @GetMapping("/api/demo/hello")
    public String say() {
        return "HelloWorld";
    }
}
