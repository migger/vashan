package ru.vashan.repository.buylist;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vashan.domain.BuyList;

import java.util.List;

@Component
public class BuyListRepositoryImpl implements BuyListRepository {
    @Autowired
    private ObjectifyFactory objectifyFactory;

    @Override
    public List<BuyList> getAll() {
        final Objectify ojy = objectifyFactory.begin();
        return ojy.load().type(BuyList.class).order("-date").list();
    }

    @Override
    public BuyList save(BuyList buyList) {
        final Objectify ofy = objectifyFactory.begin();
        ofy.save().entity(buyList).now();
        return buyList;
    }
}
