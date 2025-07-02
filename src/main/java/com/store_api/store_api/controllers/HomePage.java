package com.store_api.store_api.controllers;

import com.store_api.store_api.entities.Message;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

//@RestController
public class HomePage {
    @RequestMapping("/message")
    public Message message() {
        return new Message("Soyez les bienvenus");
    }

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("name", "Corneille ZAHIRI");
        return "index.html";
    }

    @RequestMapping("/code")
    public String code() {
        return "code.html";
    }

}
