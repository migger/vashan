package ru.vashan.web.controllers.view.edit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.vashan.domain.BuyList;
import ru.vashan.repository.buylist.BuyListRepository;

@Controller
@RequestMapping("/list/edit/{listId}/index.html")
public class BuyListEditController {
    @Autowired
    private BuyListRepository buyListRepository;

    @ModelAttribute("list")
    public BuyList load(@PathVariable("listId") Long id) {
        return buyListRepository.get(id);
    }
    @RequestMapping
    public String edit() {
        return "/jsp/buylist/edit.jspx";
    }
}
