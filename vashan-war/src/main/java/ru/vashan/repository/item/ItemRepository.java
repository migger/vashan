package ru.vashan.repository.item;

import ru.vashan.domain.Item;

import java.util.List;

public interface ItemRepository {
    List<Item> getAll();

    Item save(Item item);
}
