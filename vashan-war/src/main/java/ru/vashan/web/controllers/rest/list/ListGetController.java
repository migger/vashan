package ru.vashan.web.controllers.rest.list;

import com.googlecode.objectify.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.vashan.domain.BuyList;
import ru.vashan.repository.buylist.BuyListRepository;

@Controller
@RequestMapping("/list/get.json")
public class ListGetController {
    @Autowired
    private BuyListRepository buyListRepository;
    @RequestMapping
    @ResponseBody
    public BuyList get(Long id) {
        return buyListRepository.get(id);
    }
}
