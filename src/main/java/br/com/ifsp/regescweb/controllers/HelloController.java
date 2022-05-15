package br.com.ifsp.regescweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public ModelAndView hello(){
        ModelAndView mv = new ModelAndView("hello"); //nome do arquivo para renderização
        mv.addObject("nome", "Maria");
        return mv; //renderização arquivo em templates/hello.html;
    }

    @GetMapping("/hello-model")
    public String hello(Model model){
        model.addAttribute("nome", "Carol");
        return "hello";
    }

    @GetMapping("/hello-servlet")
    public String hello(HttpServletRequest request){
        request.setAttribute("nome", "Mariana");
        return "hello"; //renderização arquivo em templates/hello.html;
    }
}
