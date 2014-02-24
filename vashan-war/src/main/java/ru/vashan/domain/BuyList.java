package ru.vashan.domain;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

@Entity(name = "buyList")
public class BuyList {
    @Id
    private Long id;
    @Index
    private Date date;
    private String title;

    public BuyList(Date date, String title) {
        this.date = date;
        this.title = title;
    }

    public BuyList() {
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }
}
