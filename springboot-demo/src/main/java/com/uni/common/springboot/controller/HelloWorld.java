package com.uni.common.springboot.controller;

import com.uni.common.springboot.dto.UserSchoolDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("hello")
public class HelloWorld {

    @RequestMapping("world")
    public UserSchoolDTO helloWorld() {
        return UserSchoolDTO.builder().localDate(LocalDate.now()).build();
    }

}
