package ru.vashan.domain;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.OnSave;

import javax.persistence.PrePersist;
import java.util.*;

@Entity
public class Item {
    public static final int LIST_LIMIT = 5000;
    @Id()
    private Long id;
    private String title;
    @Index
    private String titleLowerCase;
    @Index
    private String[] titleIndex;

    public Item() {
    }

    public Item(String title) {
        this.title = title;
    }

    @OnSave()
    public void buildItemIndex() {
        final Set<String> index = new HashSet<>();
        if(title != null) {
            final String lowerCase = title.toLowerCase();
            for(int length = 1; length <= lowerCase.length(); length ++) {
                for(int start = 0; start + length <= lowerCase.length(); start++) {
                    index.add(lowerCase.substring(start, start + length));
                    if(index.size() > LIST_LIMIT) {
                        throw new RuntimeException("Reached index limit: " + LIST_LIMIT);
                    }
                }
            }
            titleLowerCase = title.toLowerCase();
        }
        this.titleIndex = index.toArray(new String[index.size()]);
    }



    public String getTitle() {
        return title;
    }

    public String[] getTitleIndex() {
        return titleIndex;
    }

    public String getTitleLowerCase() {
        return titleLowerCase;
    }
}
