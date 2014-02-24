package ru.vashan.web.controllers.rest.list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.vashan.domain.BuyList;
import ru.vashan.repository.buylist.BuyListRepository;

import java.util.List;

@Controller
@RequestMapping("/list/search.json")
public class ListSearchController {
    @Autowired
    private BuyListRepository buyListRepository;


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<BuyList> search() {
        return buyListRepository.getAll();
    }
}
