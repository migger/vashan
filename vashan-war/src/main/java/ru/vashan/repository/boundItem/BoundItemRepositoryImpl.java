package ru.vashan.repository.boundItem;


import com.googlecode.objectify.Key;
import org.springframework.stereotype.Component;
import ru.vashan.domain.BoundItem;
import ru.vashan.domain.BuyList;

import java.util.List;

@Component
public class BoundItemRepositoryImpl implements BoundItemRepository {
    @Override
    public List<BoundItem> getBoundToList(Key<BuyList> listId) {
        throw new RuntimeException("niy"); //todo niy
    }
}
