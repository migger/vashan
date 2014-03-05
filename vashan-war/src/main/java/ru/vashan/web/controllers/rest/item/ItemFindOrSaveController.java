package ru.vashan.web.controllers.rest.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.vashan.domain.Item;
import ru.vashan.repository.item.ItemRepository;

@Controller
@RequestMapping("/item/findOrSave/{title}.json")
public class ItemFindOrSaveController {
    @Autowired
    private ItemRepository itemRepository;

    @RequestMapping
    @ResponseBody
    public Item findOrSave(@PathVariable("title") String titile) {
        return itemRepository.findOrSave(titile);
    }
}
