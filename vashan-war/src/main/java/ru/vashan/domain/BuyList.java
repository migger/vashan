package ru.vashan.domain;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.Date;

@Entity(name = "buyList")
public class BuyList {
    @Id()
    private String id;
    private Date date;
    private String title;

    public BuyList(Date date, String title) {
        this.date = date;
        this.title = title;
    }

    public BuyList() {
    }

    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }
}
