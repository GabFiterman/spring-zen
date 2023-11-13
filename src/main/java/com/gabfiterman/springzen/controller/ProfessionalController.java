package com.gabfiterman.springzen.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabfiterman.springzen.model.Professional;

@RestController
@RequestMapping("/profissional")
public class ProfessionalController {

    @PostMapping
    public void createProfessional(@RequestBody Professional data) {
        System.out.println(data);
    }
}
