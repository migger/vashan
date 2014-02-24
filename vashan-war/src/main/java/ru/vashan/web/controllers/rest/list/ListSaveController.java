package ru.vashan.web.controllers.rest.list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.vashan.domain.BuyList;
import ru.vashan.repository.buylist.BuyListRepository;

@Controller
@RequestMapping("/list/save.json")
public class ListSaveController {
    @Autowired
    private BuyListRepository buyListRepository;


    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public BuyList save(@RequestBody BuyList buyList) {
        return buyListRepository.save(buyList);
    }
}
