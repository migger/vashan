package ru.vashan.repository.boundItem;

import com.googlecode.objectify.Key;
import ru.vashan.domain.BoundItem;
import ru.vashan.domain.BuyList;

import java.util.List;

public interface BoundItemRepository {
    List<BoundItem> getBoundToList(Key<BuyList> listId);
}
