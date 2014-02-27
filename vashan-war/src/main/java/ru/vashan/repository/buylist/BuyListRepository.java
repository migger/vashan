package ru.vashan.repository.buylist;

import com.googlecode.objectify.Key;
import ru.vashan.domain.BuyList;

import java.util.List;

public interface BuyListRepository {
    List<BuyList> getAll();
    BuyList save(BuyList buyList);

    BuyList get(long key);
}
