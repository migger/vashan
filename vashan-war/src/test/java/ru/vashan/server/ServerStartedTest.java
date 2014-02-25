package ru.vashan.server;

import com.google.appengine.tools.development.testing.BaseDevAppServerTestConfig;
import com.google.appengine.tools.development.testing.DevAppServerTest;
import com.google.appengine.tools.development.testing.DevAppServerTestRunner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.vashan.domain.BuyList;
import ru.vashan.server.testinfra.BaseServerTest;
import ru.vashan.server.testinfra.TestConfig;
import ru.vashan.web.controllers.rest.list.ListSaveController;
import ru.vashan.web.controllers.rest.list.ListSearchController;

import java.net.*;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;

@RunWith(DevAppServerTestRunner.class)
@DevAppServerTest(TestConfig.class)
public class ServerStartedTest extends BaseServerTest {
    public static final Logger LOGGER = Logger.getLogger(ServerStartedTest.class.getName());
    private Gson gson = new GsonBuilder().create();

    @Test
    public void testListSearch() throws Exception {
        final String content = getPageContent(getPage(ListSearchController.class)).trim();
        final List list = gson.fromJson(content, List.class);
        Assert.assertEquals(0, list.size());
    }

    @Test
    public void testListCreate() throws Exception {
        final BuyList buyList = new BuyList(new Date(), "TEST");
        final BuyList content = doPutAndGetContentJSon(getPage(ListSaveController.class), buyList, BuyList.class);
        Assert.assertNotNull(content.getId());

    }

    private String getPage(Class<?> aClass) {
        final RequestMapping annotation = aClass.getAnnotation(RequestMapping.class);
        Assert.assertNotNull(annotation);
        final String[] value = annotation.value();
        Assert.assertEquals(1, value.length);
        return value[0];
    }

//    @Test
//    public void testWait() throws Exception {
//        final Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
//        System.out.format("redir --lport=9090 --cport=%s --laddr=0.0.0.0 --caddr=localhost", System.getProperty(BaseDevAppServerTestConfig.DEFAULT_PORT_SYSTEM_PROPERTY));
//
//        Thread.currentThread().join();
//    }
}