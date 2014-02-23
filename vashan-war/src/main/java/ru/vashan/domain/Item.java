package ru.vashan.domain;

import com.googlecode.objectify.annotation.Id;

public class Item {
    @Id()
    private Long id;
    private String title;

    public Item() {
    }

    public Item(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
