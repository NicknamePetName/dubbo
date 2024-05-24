package com.youkeda.exercise.dem.api.control;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
public class HelloController {
    @GetMapping("/hello")
    public String say() {
        return "HelloWorld";
    }
}
