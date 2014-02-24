package ru.vashan.web.controllers.rest.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.vashan.domain.Item;
import ru.vashan.repository.item.ItemRepository;

@Controller
@RequestMapping("/item/save.json")
public class ItemSaveController {
    @Autowired
    private ItemRepository itemRepository;

    @RequestMapping
    @ResponseBody
    public Item save(@RequestBody Item item) {
        return itemRepository.save(item);
    }
}
