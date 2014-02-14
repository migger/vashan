package ru.vashan.web.controllers.list;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class ListSearchControllerTest {
    @Configuration
    static class ContextConfiguration {
        public ListSearchController listSearchController() {
            return new ListSearchController();
        }
    }

    @Autowired
    private ListSearchController listSearchController;

    @Test
    public void testIsComponent() throws Exception {
        Assert.assertNotNull(listSearchController.getClass().getAnnotation(Component.class));
    }
}
