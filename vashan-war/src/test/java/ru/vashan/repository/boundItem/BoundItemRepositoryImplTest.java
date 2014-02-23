package ru.vashan.repository.boundItem;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.Result;
import com.googlecode.objectify.cmd.LoadType;
import com.googlecode.objectify.cmd.Loader;
import com.googlecode.objectify.cmd.Saver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.vashan.domain.BoundItem;
import ru.vashan.domain.BuyList;
import ru.vashan.repository.buylist.BuyListRepository;
import ru.vashan.repository.buylist.BuyListRepositoryImpl;
import ru.vashan.server.testinfra.BaseChecks;
import ru.vashan.web.controllers.Excluded;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class BoundItemRepositoryImplTest {
    private Objectify ojy;
    private Loader loader;
    private Saver saver;

    @Configuration
    @Excluded
    static class ContextConfiguration {
        @Bean
        public BoundItemRepository boundItemRepository() {
            return new BoundItemRepositoryImpl();
        }

        @Bean
        public ObjectifyFactory objectifyFactory() {
            return mock(ObjectifyFactory.class);
        }
    }


    @Autowired
    private BoundItemRepository boundItemRepository;

    @Autowired
    private ObjectifyFactory objectifyFactory;

    @Before
    public void setUp() throws Exception {
        reset(objectifyFactory);

        ojy = mock(Objectify.class);
        when(objectifyFactory.begin()).thenReturn(ojy);
        loader = mock(Loader.class);
        when(ojy.load()).thenReturn(loader);
        saver = mock(Saver.class);
        when(ojy.save()).thenReturn(saver);
    }

    @Test
    public void testIsComponent() throws Exception {
        BaseChecks.assertIsAComponent(boundItemRepository.getClass());;
    }

    @Test
    public void testGetAll() throws Exception {
        final LoadType<BoundItem> loadType = mock(LoadType.class);
        when(loader.type(BoundItem.class)).thenReturn(loadType);
        final BoundItem buyList1 = mock(BoundItem.class);
        final BoundItem buyList2 = mock(BoundItem.class);
        when(loadType.list()).thenReturn(Arrays.asList(
                buyList1,
                buyList2
        ));
//        final List<BoundItem> all = boundItemRepository.getBoundToList();
//        assertEquals(2, all.size());
//        assertEquals(buyList1, all.get(0));
//        assertEquals(buyList2, all.get(1));
    }

    @Test
    public void testSave() throws Exception {
//        final BuyList input = mock(BuyList.class);
//        final Result result = mock(Result.class);
//        when(saver.entity(input)).thenReturn(result);
//        buyListRepository.save(input);
//        verify(saver).entity(input);
//        verify(result).now();
    }
}
