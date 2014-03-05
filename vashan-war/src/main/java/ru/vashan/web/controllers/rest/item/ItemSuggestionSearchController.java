package ru.vashan.web.controllers.rest.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.vashan.domain.Item;
import ru.vashan.repository.item.ItemRepository;
import ru.vashan.transport.ItemSuggestion;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/item/search/{query}.json")
public class ItemSuggestionSearchController {
    @Autowired
    private ItemRepository itemRepository;

    @RequestMapping
    @ResponseBody
    public List<ItemSuggestion> search(@PathVariable("query") String query) {
        final List<Item> list = itemRepository.search(query);
        final ArrayList<ItemSuggestion> suggestions = new ArrayList<>(list.size());
        for (Item item : list) {
            suggestions.add(new ItemSuggestion(item.getTitle()));
        }
        return suggestions;
    }
}
