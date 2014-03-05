package ru.vashan.web.controllers.rest.item;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.vashan.domain.Item;
import ru.vashan.repository.item.ItemRepository;
import ru.vashan.server.testinfra.BaseChecks;
import ru.vashan.server.testinfra.MethodChecker;
import ru.vashan.transport.ItemSuggestion;
import ru.vashan.web.controllers.Excluded;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class ItemSuggestionSearchControllerTest {
    @Configuration
    @Excluded
    static class ContextConfiguration {
        @Bean
        public ItemSuggestionSearchController itemListController() {
            return new ItemSuggestionSearchController();
        }

        @Bean
        public ItemRepository buyListRepository() {
            return mock(ItemRepository.class);
        }
    }

    @Autowired
    private ItemSuggestionSearchController itemSuggestionSearchController;
    @Autowired
    private ItemRepository itemRepository;

    @Before
    public void setUp() throws Exception {
        reset(itemRepository);
    }

    @Test
    public void testIsController() throws Exception {
        BaseChecks.assertIsAController("/item/search/{query}.json", itemSuggestionSearchController.getClass());
        BaseChecks.ckeckMethod(itemSuggestionSearchController.getClass(), new Class[]{String.class}, "search", new MethodChecker(){
            @Override
            public void checkMethod(Method method) {
                org.junit.Assert.assertNotNull(method.getAnnotation(RequestMapping.class));
                org.junit.Assert.assertNotNull(method.getAnnotation(ResponseBody.class));
            }

            @Override
            protected void checkParameter1(Parameter parameter) {
                final PathVariable pathVariable = parameter.getAnnotation(PathVariable.class);
                org.junit.Assert.assertNotNull(pathVariable);
                org.junit.Assert.assertEquals("query", pathVariable.value());
            }
        });

    }

    @Test
    public void testList() throws Exception {
        final Item item1 = new Item("item1");
        final Item item2 = new Item("item2");
        final List<Item> expected = Arrays.asList(item1, item2);
        final String query = "QUERY";
        when(itemRepository.search(query)).thenReturn(expected);
        final List<ItemSuggestion> search = itemSuggestionSearchController.search(query);
        Assert.assertEquals(2, search.size());
        Assert.assertTrue(search.contains(new ItemSuggestion(item1.getTitle())));
        Assert.assertTrue(search.contains(new ItemSuggestion(item2.getTitle())));

    }
}
