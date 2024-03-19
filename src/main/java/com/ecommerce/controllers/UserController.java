package com.ecommerce.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.models.User;

@RestController
@RequestMapping("/")
public class UserController {

    @GetMapping
    public String home() {
        return "home page";
    }

    @GetMapping("/userinfo")
    public Object sayHello(Authentication authentication) {
        return authentication.getPrincipal();
    }

    @GetMapping("/usuario")
    public String testando() {
        return "permissao usuario";
    }
}
