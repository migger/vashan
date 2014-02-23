package ru.vashan.web.controllers.item;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.vashan.domain.Item;
import ru.vashan.repository.item.ItemRepository;
import ru.vashan.server.testinfra.BaseChecks;
import ru.vashan.server.testinfra.MethodChecker;
import ru.vashan.web.controllers.Excluded;

import java.lang.reflect.Method;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class ItemSaveControllerTest {
    @Configuration
    @Excluded
    static class ContextConfiguration {
        @Bean
        public ItemSaveController itemSaveController() {
            return new ItemSaveController();
        }

        @Bean
        public ItemRepository buyListRepository() {
            return mock(ItemRepository.class);
        }
    }

    @Autowired
    private ItemSaveController itemSaveController;
    @Autowired
    private ItemRepository itemRepository;

    @Before
    public void setUp() throws Exception {
        reset(itemRepository);
    }

    @Test
    public void testIsController() throws Exception {
        BaseChecks.assertIsAController("/item/save.json", itemSaveController.getClass());
        BaseChecks.ckeckMethod(itemSaveController.getClass(), new Class[]{Item.class}, "save", new MethodChecker(){
            @Override
            public void checkMethod(Method method) {
                org.junit.Assert.assertNotNull(method.getAnnotation(RequestMapping.class));
                org.junit.Assert.assertNotNull(method.getAnnotation(ResponseBody.class));
            }

            @Override
            protected void checkParameter1(Parameter parameter) {
                org.junit.Assert.assertNotNull(parameter.getAnnotation(RequestBody.class));
            }
        });

    }

    @Test
    public void testList() throws Exception {
        final Item expected = mock(Item.class);
        final Item item = mock(Item.class);
        when(itemRepository.save(item)).thenReturn(expected);
        Assert.assertEquals(expected, itemSaveController.save(item));

    }
}
