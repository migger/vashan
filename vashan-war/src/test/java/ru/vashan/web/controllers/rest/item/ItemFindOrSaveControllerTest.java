package ru.vashan.web.controllers.rest.item;

import org.junit.Assert;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.vashan.domain.Item;
import ru.vashan.repository.item.ItemRepository;
import ru.vashan.server.testinfra.BaseChecks;
import ru.vashan.server.testinfra.MethodChecker;
import ru.vashan.web.controllers.Excluded;

import java.lang.reflect.Method;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class ItemFindOrSaveControllerTest {
    @Configuration
    @Excluded
    static class ContextConfiguration {
        @Bean
        public ItemFindOrSaveController itemSaveController() {
            return new ItemFindOrSaveController();
        }

        @Bean
        public ItemRepository buyListRepository() {
            return mock(ItemRepository.class);
        }
    }

    @Autowired
    private ItemFindOrSaveController itemFindOrSaveController;
    @Autowired
    private ItemRepository itemRepository;

    @Before
    public void setUp() throws Exception {
        reset(itemRepository);
    }

    @Test
    public void testIsController() throws Exception {
        BaseChecks.assertIsAController("/item/findOrSave/{title}.json", itemFindOrSaveController.getClass());
        BaseChecks.ckeckMethod(itemFindOrSaveController.getClass(), new Class[]{Item.class}, "findOrSave", new MethodChecker(){
            @Override
            public void checkMethod(Method method) {
                Assert.assertNotNull(method.getAnnotation(RequestMapping.class));
                Assert.assertNotNull(method.getAnnotation(ResponseBody.class));
            }

            @Override
            protected void checkParameter1(Parameter parameter) {
                Assert.assertEquals("title", parameter.getAnnotation(PathVariable.class).value());
            }
        });

    }

    @Test
    public void testList() throws Exception {
        final Item expected = mock(Item.class);
        final String item = mock(String.class);
        when(itemRepository.findOrSave(item)).thenReturn(expected);
        Assert.assertEquals(expected, itemFindOrSaveController.findOrSave(item));

    }
}
