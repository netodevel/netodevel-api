package com.example.netodevelapi;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloResource {

    @GetMapping
    public String index() {
        return "hello k8s from jenkins CI/CD";
    }

}
