package br.com.ifsp.regescweb.controllers;

import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

public class HelloController {

    @GetMapping("/hello")
    public String hello(HttpServletRequest request){
        request.setAttribute(name:"nome", o:"Ana Maria");
        return "hello"; //renderização arquivo em templates/hello.html
    }
}
