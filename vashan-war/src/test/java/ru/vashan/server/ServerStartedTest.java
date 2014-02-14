package ru.vashan.server;

import junit.framework.Assert;
import org.junit.Test;
import ru.vashan.server.testinfra.BaseServerTest;

import java.util.logging.Logger;

public class ServerStartedTest extends BaseServerTest {
    public static final Logger LOGGER = Logger.getLogger(ServerStartedTest.class.getName());
    @Test
    public void testEndToEnd() throws Exception {
        getPageContent("/");
    }

    @Test
    public void testListSearch() throws Exception {
        final String content = getPageContent("/list/search.json").trim();
        Assert.assertTrue(content.startsWith("[") && content.endsWith("]"));
    }

}