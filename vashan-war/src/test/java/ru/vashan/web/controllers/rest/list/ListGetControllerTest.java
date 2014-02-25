package ru.vashan.web.controllers.rest.list;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalURLFetchServiceTestConfig;
import com.googlecode.objectify.Key;
import org.junit.After;
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
import ru.vashan.domain.BuyList;
import ru.vashan.repository.buylist.BuyListRepository;
import ru.vashan.server.testinfra.BaseChecks;
import ru.vashan.server.testinfra.MethodChecker;
import ru.vashan.web.controllers.Excluded;

import java.lang.reflect.Method;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class ListGetControllerTest {
    protected final LocalServiceTestHelper dataStore = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Configuration
    @Excluded
    static class ContextConfiguration {
        @Bean
        public ListGetController listGetController() {
            return new ListGetController();
        }

        @Bean
        public BuyListRepository buyListRepository() {
            return mock(BuyListRepository.class);
        }
    }

    @Autowired
    private ListGetController listGetController;
    @Autowired
    private BuyListRepository buyListRepository;

    @Before
    public void setUp() throws Exception {
        dataStore.setUp();
        reset(buyListRepository);
    }

    @After
    public void tearDown() throws Exception {
        dataStore.tearDown();
    }

    @Test
    public void testIsController() throws Exception {
        BaseChecks.assertIsAController("/list/get.json", listGetController.getClass());
        BaseChecks.ckeckMethod(listGetController.getClass(), new Class[]{Long.class}, "get", new MethodChecker(){
            @Override
            public void checkMethod(Method method) {
                Assert.assertNotNull(method.getAnnotation(RequestMapping.class));
                Assert.assertNotNull(method.getAnnotation(ResponseBody.class));
            }
        });
    }

    @Test
    public void testReturnList() throws Exception {
        final BuyList expected = mock(BuyList.class);
        final Key<BuyList> key = Key.create(BuyList.class, 123L);
        when(buyListRepository.get(key)).thenReturn(expected);
        Assert.assertEquals(expected, listGetController.get(123L));
    }
}
