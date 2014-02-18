package ru.vashan.repository.buylist;

import ru.vashan.domain.BuyList;

import java.util.List;

public interface BuyListRepository {
    List<BuyList> getAll();
    BuyList save(BuyList buyList);
}
