package ru.vashan.web.controllers.view.edit;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/list/edit.html")
public class BuyListEditController {
    @RequestMapping
    public String edit() {
        return "/jsp/buylist/edit.jspx";
    }
}
