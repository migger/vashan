package ru.vashan.repository.item;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.Result;
import com.googlecode.objectify.cmd.LoadType;
import com.googlecode.objectify.cmd.Loader;
import com.googlecode.objectify.cmd.Saver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.vashan.domain.BuyList;
import ru.vashan.domain.Item;
import ru.vashan.server.testinfra.BaseChecks;
import ru.vashan.web.controllers.Excluded;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class ItemRepositoryImplTest {
    private Objectify ojy;
    private Loader loader;
    private Saver saver;

    @Configuration
    @Excluded
    static class ContextConfiguration {
        @Bean
        public ItemRepository itemRepository() {
            return new ItemRepositoryImpl();
        }

        @Bean
        public ObjectifyFactory objectifyFactory() {
            return mock(ObjectifyFactory.class);
        }
    }

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


    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void testComponent() throws Exception {
        BaseChecks.assertIsAComponent(itemRepository.getClass());
    }

    @Test
    public void testGetAll() throws Exception {
        final LoadType<Item> loadType = mock(LoadType.class);
        when(loader.type(Item.class)).thenReturn(loadType);
        final Item item1 = mock(Item.class);
        final Item item2 = mock(Item.class);
        when(loadType.list()).thenReturn(Arrays.asList(
                item1,
                item2
        ));
        final List<Item> all = itemRepository.getAll();
        assertEquals(2, all.size());
        assertEquals(item1, all.get(0));
        assertEquals(item2, all.get(1));

    }
    @Test
    public void testSave() throws Exception {
        final Item input = mock(Item.class);
        final Result result = mock(Result.class);
        when(saver.entity(input)).thenReturn(result);
        itemRepository.save(input);
        verify(saver).entity(input);
        verify(result).now();


    }

}
