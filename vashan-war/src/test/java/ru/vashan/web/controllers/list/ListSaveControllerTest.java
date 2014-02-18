package ru.vashan.web.controllers.list;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.vashan.domain.BuyList;
import ru.vashan.repository.buylist.BuyListRepository;

import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class ListSaveControllerTest {
    @Configuration
    static class ContextConfiguration {
        @Bean
        public ListSaveController listSearchController() {
            return new ListSaveController();
        }

        @Bean
        public BuyListRepository buyListRepository() {
            return mock(BuyListRepository.class);
        }
    }

    @Autowired
    private ListSaveController listSaveController;
    @Autowired
    private BuyListRepository buyListRepository;

    @Before
    public void setUp() throws Exception {
        reset(buyListRepository);
    }

    @Test
    public void testIsController() throws Exception {
        Assert.assertNotNull(listSaveController.getClass().getAnnotation(Controller.class));
        final RequestMapping requestMapping = listSaveController.getClass().getAnnotation(RequestMapping.class);
        Assert.assertNotNull(requestMapping);
        Assert.assertEquals("/list/save.json", requestMapping.value()[0]);
    }

    @Test
    public void testReturnList() throws Exception {
        final BuyList expected = mock(BuyList.class);
        final BuyList input = mock(BuyList.class);
        when(buyListRepository.save(input)).thenReturn(expected);
        Assert.assertEquals(expected, listSaveController.save(input));
    }
}
