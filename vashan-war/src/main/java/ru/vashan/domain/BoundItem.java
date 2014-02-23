package ru.vashan.domain;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Id;

public class BoundItem {
    @Id
    private Long id;

    private Key<Item> itemId;
    private Key<BuyList> listId;

    public BoundItem() {
    }

    public BoundItem(Key<BuyList> listId, Key<Item> itemId) {
        this.itemId = itemId;
        this.listId = listId;
    }

    public Key<Item> getItemId() {
        return itemId;
    }

    public Key<BuyList> getListId() {
        return listId;
    }
}
