package ru.vashan.repository.item;

import ru.vashan.domain.Item;
import ru.vashan.transport.ItemSuggestion;

import java.util.List;

public interface ItemRepository {
    List<Item> getAll();

    Item findOrSave(String item);

    List<Item> search(String query);
}
