package ru.vashan.web.controllers.view.list;

import org.mortbay.jetty.MimeTypes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index.html")
public class MainController {
    @RequestMapping(produces = MimeTypes.TEXT_HTML)
    public String index() {
        return "/jsp/buylist/list.jspx";
    }
}
