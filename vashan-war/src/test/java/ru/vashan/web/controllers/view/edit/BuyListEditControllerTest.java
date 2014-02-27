package ru.vashan.web.controllers.view.edit;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.vashan.domain.BuyList;
import ru.vashan.repository.buylist.BuyListRepository;
import ru.vashan.server.testinfra.BaseChecks;
import ru.vashan.server.testinfra.MethodChecker;
import ru.vashan.web.controllers.Excluded;

import java.lang.reflect.Method;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class BuyListEditControllerTest {
    @Configuration
    @Excluded
    static class ContextConfiguration {

        @Bean
        public BuyListEditController mainController() {
            return new BuyListEditController();
        }
        @Bean
        public BuyListRepository buyListRepository() {
            return mock(BuyListRepository.class);
        }
    }
    @Autowired
    private BuyListRepository buyListRepository;
    @Autowired
    private BuyListEditController buyListEditController;

    @Before
    public void setUp() throws Exception {
        reset(buyListRepository);

    }

    @Test
    public void testController() throws Exception {
        BaseChecks.assertIsAController("/list/edit/{listId}/index.html", buyListEditController.getClass());
        BaseChecks.ckeckMethod(buyListEditController.getClass(),
                new Class[0], "edit", new MethodChecker() {
            @Override
            public void checkMethod(Method method) {
                Assert.assertNotNull(method.getAnnotation(RequestMapping.class));
            }
        });
        BaseChecks.ckeckMethod(buyListEditController.getClass(),
                new Class[]{Long.class}, "load", new MethodChecker() {
            @Override
            protected void checkParameter1(Parameter parameter) {
                final PathVariable pathVariable = parameter.getAnnotation(PathVariable.class);
                Assert.assertNotNull(pathVariable);
                Assert.assertEquals("listId", pathVariable.value());
            }

            @Override
            public void checkMethod(Method method) {
                final ModelAttribute modelAttribute = method.getAnnotation(ModelAttribute.class);
                Assert.assertNotNull(modelAttribute);
                Assert.assertEquals("list", modelAttribute.value());
            }
        });
    }

    @Test
    public void testReturnValidJsp() throws Exception {
        Assert.assertEquals("/jsp/buylist/edit.jspx", buyListEditController.edit());
    }

    @Test
    public void testGetItem() throws Exception {
        final BuyList buyList = mock(BuyList.class);
        when (buyListRepository.get(123L)).thenReturn(buyList);
        Assert.assertEquals(buyList, buyListEditController.load(123L));
    }
}
