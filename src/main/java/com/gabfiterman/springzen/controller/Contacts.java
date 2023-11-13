package com.gabfiterman.springzen.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contatos")
public class Contacts {
    
    @PostMapping
    public void createContact(@RequestBody String json) {
        System.out.println(json);
    }
}
