package ru.vashan.web.controllers.rest.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.vashan.domain.Item;
import ru.vashan.repository.item.ItemRepository;

import java.util.List;

@Controller
@RequestMapping("/item/list.json")
public class ItemListController {
    @Autowired
    private ItemRepository itemRepository;

    @RequestMapping
    @ResponseBody
    public List<Item> list() {
        return itemRepository.getAll();
    }
}
