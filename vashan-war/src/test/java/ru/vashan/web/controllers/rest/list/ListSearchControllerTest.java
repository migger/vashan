package ru.vashan.web.controllers.rest.list;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.vashan.repository.buylist.BuyListRepository;
import ru.vashan.server.testinfra.BaseChecks;
import ru.vashan.server.testinfra.MethodChecker;
import ru.vashan.web.controllers.Excluded;

import java.lang.reflect.Method;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class ListSearchControllerTest {
    @Configuration
    @Excluded
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
        BaseChecks.assertIsAController("/list/search.json", listSearchController.getClass());
        BaseChecks.ckeckMethod(listSearchController.getClass(), new Class[]{}, "search", new MethodChecker(){
            @Override
            public void checkMethod(Method method) {
                Assert.assertNotNull(method.getAnnotation(RequestMapping.class));
                Assert.assertNotNull(method.getAnnotation(ResponseBody.class));
            }

            @Override
            protected void checkParameter1(Parameter parameter) {
                Assert.assertNotNull(parameter.getAnnotation(RequestBody.class));
            }
        });

    }

    @Test
    public void testReturnList() throws Exception {
        final List expected = mock(List.class);
        when(buyListRepository.getAll()).thenReturn(expected);
        Assert.assertEquals(expected, listSearchController.search());
    }
}
