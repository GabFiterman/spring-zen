package com.gabfiterman.springzen.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabfiterman.springzen.model.Contact;

@RestController
@RequestMapping("/contatos")
public class ContactController {
    
    @PostMapping
    public void createContact(@RequestBody Contact data) {
        System.out.println(data);
    }
}
