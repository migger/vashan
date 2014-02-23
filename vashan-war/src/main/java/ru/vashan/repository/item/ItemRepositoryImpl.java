package ru.vashan.repository.item;


import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vashan.domain.Item;

import java.util.List;

@Component
public class ItemRepositoryImpl implements ItemRepository {
    @Autowired
    private ObjectifyFactory objectifyFactory;
    @Override
    public List<Item> getAll() {
        final Objectify ofy = objectifyFactory.begin();
        return ofy.load().type(Item.class).list();
    }

    @Override
    public Item save(Item item) {
        final Objectify ofy = objectifyFactory.begin();
        ofy.save().entity(item).now();
        return item;
    }
}
