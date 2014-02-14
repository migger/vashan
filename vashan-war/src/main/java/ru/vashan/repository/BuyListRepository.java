package ru.vashan.repository;

import ru.vashan.domain.BuyList;

import java.util.List;

public interface BuyListRepository {
    List<BuyList> getAll();
}
