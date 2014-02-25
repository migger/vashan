package ru.vashan.web.controllers.view.list;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.vashan.server.testinfra.BaseChecks;
import ru.vashan.server.testinfra.MethodChecker;

import java.lang.reflect.Method;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class BuyListListControllerTest {
    @Configuration
    static class ContextConfiguration {
        @Bean
        public BuyListListController mainController() {
            return new BuyListListController();
        }
    }
    @Autowired
    private BuyListListController buyListListController;

    @Test
    public void testController() throws Exception {
        BaseChecks.assertIsAController("/index.html", buyListListController.getClass());
        BaseChecks.ckeckMethod(buyListListController.getClass(),
                new Class[0], "index", new MethodChecker() {
            @Override
            public void checkMethod(Method method) {
                Assert.assertNotNull(method.getAnnotation(RequestMapping.class));
            }
        });
    }

    @Test
    public void testReturnValidJsp() throws Exception {
        Assert.assertEquals("/jsp/buylist/list.jspx", buyListListController.index());

    }
}
