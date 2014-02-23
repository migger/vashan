package ru.vashan.web.controllers.bounditem;

import com.googlecode.objectify.Key;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.vashan.domain.BuyList;
import ru.vashan.repository.boundItem.BoundItemRepository;
import ru.vashan.server.testinfra.BaseChecks;
import ru.vashan.server.testinfra.MethodChecker;
import ru.vashan.web.controllers.Excluded;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class BoundItemByListControllerTest {
    @Configuration
    @Excluded
    static class ContextConfiguration {
        @Bean
        public BoundItemByListController boundItemController() {
            return new BoundItemByListController();
        }
        @Bean
        public BoundItemRepository boundItemRepository() {
            return mock(BoundItemRepository.class);
        }
    }
    @Autowired
    private BoundItemByListController boundItemByListController;
    @Autowired
    private BoundItemRepository boundItemRepository;

    @Before
    public void setUp() throws Exception {
        reset(boundItemRepository);
    }

    @Test
    public void testController() throws Exception {
        BaseChecks.assertIsAController("/boundItem/byList/${listId}", boundItemByListController.getClass());
        BaseChecks.ckeckMethod(boundItemByListController.getClass(), new Class[]{Long.TYPE}, "listBound", new MethodChecker() {
            @Override
            public void checkMethod(Method method) {
                Assert.assertNotNull(method.getAnnotation(ResponseBody.class));
                Assert.assertNotNull(method.getAnnotation(RequestMapping.class));
            }



            @Override
            public void checkParameter1(Parameter parameter) {
                final PathVariable annotation = parameter.getAnnotation(PathVariable.class);
                Assert.assertNotNull(annotation);
                Assert.assertEquals("listId", annotation.value());
            }
        });
    }

    @Test
    public void testGetByList() throws Exception {
        final List expected = mock(List.class);
        when(boundItemRepository.getBoundToList(Key.create(BuyList.class, 123L))).thenReturn(expected);
        Assert.assertEquals(expected, boundItemByListController.listBound(123L));

    }
}
