package ru.vashan.repository.item;


import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.Work;
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
    public Item findOrSave(final String item) {
        return objectifyFactory.begin().transact(new Work<Item>() {
            @Override
            public Item run() {
                final Objectify objectify = objectifyFactory.begin();
                final Item existingItem = objectify.load()
                        .type(Item.class)
                        .filter("titleLowerCase = ", item.toLowerCase())
                        .first().now();
                if(existingItem != null) {
                    return existingItem;
                }
                final Item entity = new Item(item);
                objectify.save().entity(entity).now();
                return entity;
            }
        });
    }

    @Override
    public List<Item> search(String query) {
        final Objectify ofy = objectifyFactory.begin();
        return ofy.load().type(Item.class).filter("titleIndex = ", query.toLowerCase()).list();
    }
}
