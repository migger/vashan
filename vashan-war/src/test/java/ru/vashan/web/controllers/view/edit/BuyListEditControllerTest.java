package ru.vashan.web.controllers.view.edit;

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
import ru.vashan.web.controllers.view.list.BuyListListController;

import java.lang.reflect.Method;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class BuyListEditControllerTest {
    @Configuration
    static class ContextConfiguration {
        @Bean
        public BuyListEditController mainController() {
            return new BuyListEditController();
        }
    }
    @Autowired
    private BuyListEditController buyListEditController;

    @Test
    public void testController() throws Exception {
        BaseChecks.assertIsAController("/list/edit.html", buyListEditController.getClass());
        BaseChecks.ckeckMethod(buyListEditController.getClass(),
                new Class[0], "edit", new MethodChecker() {
            @Override
            public void checkMethod(Method method) {
                Assert.assertNotNull(method.getAnnotation(RequestMapping.class));
            }
        });
    }

    @Test
    public void testReturnValidJsp() throws Exception {
        Assert.assertEquals("/jsp/buylist/edit.jspx", buyListEditController.edit());

    }
}
