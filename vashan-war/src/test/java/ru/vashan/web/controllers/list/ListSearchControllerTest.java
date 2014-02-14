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
import ru.vashan.repository.BuyListRepository;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class ListSearchControllerTest {
    @Configuration
    static class ContextConfiguration {
        @Bean
        public ListSearchController listSearchController() {
            return new ListSearchController();
        }

        @Bean
        public BuyListRepository buyListRepository() {
            return mock(BuyListRepository.class);
        }
    }

    @Autowired
    private ListSearchController listSearchController;
    @Autowired
    private BuyListRepository buyListRepository;

    @Before
    public void setUp() throws Exception {
        reset(buyListRepository);
    }

    @Test
    public void testIsController() throws Exception {
        Assert.assertNotNull(listSearchController.getClass().getAnnotation(Controller.class));
        final RequestMapping requestMapping = listSearchController.getClass().getAnnotation(RequestMapping.class);
        Assert.assertNotNull(requestMapping);
        Assert.assertEquals("/list/search.json", requestMapping.value()[0]);
    }

    @Test
    public void testReturnList() throws Exception {
        final List expected = mock(List.class);
        when(buyListRepository.getAll()).thenReturn(expected);
        Assert.assertEquals(expected, listSearchController.search());
    }
}
