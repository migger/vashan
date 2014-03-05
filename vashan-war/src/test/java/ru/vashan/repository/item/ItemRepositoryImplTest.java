package ru.vashan.repository.item;

import com.googlecode.objectify.*;
import com.googlecode.objectify.cmd.LoadType;
import com.googlecode.objectify.cmd.Loader;
import com.googlecode.objectify.cmd.Query;
import com.googlecode.objectify.cmd.Saver;
import junit.framework.Assert;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
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
    private Objectify ofy;
    private Objectify ofyT;
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

        ofy = mock(Objectify.class, "Objectify");
        ofyT = mock(Objectify.class, "Objectify within transaction");

        when(objectifyFactory.begin()).thenReturn(ofy, ofyT);
        loader = mock(Loader.class);
        when(ofy.load()).thenReturn(loader);
        saver = mock(Saver.class);
        when(ofy.save()).thenReturn(saver);

        when(ofy.transact(Mockito.<Work<Item>>any())).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Work<Item> work = (Work<Item>) invocationOnMock.getArguments()[0];
                return work.run();
            }
        });
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
    public void testSearch() throws Exception {
        final LoadType<Item> loadType = mock(LoadType.class);
        when(loader.type(Item.class)).thenReturn(loadType);
        final List expected = mock(List.class);
        final String query = "QUERY";
        final LoadType loadType2 = mock(LoadType.class);
        when(loadType.filter("titleIndex = ", query.toLowerCase())).thenReturn(loadType2);
        when(loadType2.list()).thenReturn(expected);
        assertEquals(expected, itemRepository.search(query));
    }
    @Test
    public void testSave() throws Exception {
        final String input = "TITLE";

        prepareSave(input);

        final Saver saver = mock(Saver.class);
        when(ofyT.save()).thenReturn(saver);

        final Result<Key<Item>> result = mock(Result.class);
        when(saver.entity(argThat(new BaseMatcher<Item>() {
            @Override
            public boolean matches(Object o) {
                Item item = (Item) o;

                return input.equals(item.getTitle());
            }

            @Override
            public void describeTo(Description description) {
            }
        }))).thenReturn(result);

        Assert.assertEquals(input, itemRepository.findOrSave(input).getTitle());

        verify(result).now();
    }

    @Test
    public void testFind() throws Exception {
        final String input = "TITLE";
        final Item expected = mock(Item.class);
        when(prepareSave(input).now()).thenReturn(expected);
        Assert.assertEquals(expected, itemRepository.findOrSave(input));
    }

    private LoadResult<Item> prepareSave(String input) {
        final Loader loader = mock(Loader.class);
        when(ofyT.load()).thenReturn(loader);

        final LoadType<Item> loadType = mock(LoadType.class);
        when(loader.type(Item.class)).thenReturn(loadType);

        final Query<Item> query = mock(Query.class);
        when(loadType.filter("titleLowerCase = ", input.toLowerCase())).thenReturn(query);

        final LoadResult<Item> loadResult = mock(LoadResult.class);
        when(query.first()).thenReturn(loadResult);
        return loadResult;
    }

}
