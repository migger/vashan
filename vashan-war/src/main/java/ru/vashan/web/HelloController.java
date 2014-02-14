package ru.vashan.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/index.html")
public class HelloController {
    @RequestMapping(method = RequestMethod.GET,  produces = "text/plain")
    public String sayHello() {
        return "Hello, vashan";
    }
}
