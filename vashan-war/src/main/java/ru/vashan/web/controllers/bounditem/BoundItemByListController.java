package ru.vashan.web.controllers.bounditem;

import com.googlecode.objectify.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.vashan.domain.BoundItem;
import ru.vashan.domain.BuyList;
import ru.vashan.repository.boundItem.BoundItemRepository;

import java.util.List;

@Controller
@RequestMapping("/boundItem/byList/${listId}")
public class BoundItemByListController {
    @Autowired
    private BoundItemRepository boundItemRepository;

    @ResponseBody
    @RequestMapping()
    public List<BoundItem> listBound(@PathVariable("listId") long listId) {
        return boundItemRepository.getBoundToList(Key.create(BuyList.class, listId));
    }
}
