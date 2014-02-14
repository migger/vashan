package ru.vashan.server.testinfra;

import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.appengine.tools.development.testing.*;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

@RunWith(DevAppServerTestRunnerImproved.class)
@DevAppServerTest(TestConfig.class)
public class BaseServerTest {
    private static final Logger LOG = Logger.getLogger(BaseServerTest.class.getName());
    protected final LocalServiceTestHelper testHelper = new LocalServiceTestHelper(new LocalURLFetchServiceTestConfig(), new LocalDatastoreServiceTestConfig());

    @Before
    public void setUp() {
        testHelper.setUp();
    }

    @After
    public void tearDown() {
        testHelper.tearDown();
    }

    @AfterClass
    public static void tearDownServer() {
        //shutdown Test server;
    }



    protected String getBaseUrl() {
        return "http://localhost:" + System.getProperty(BaseDevAppServerTestConfig.DEFAULT_PORT_SYSTEM_PROPERTY);
    }

    protected String getPageContent(String page) throws IOException {
        final URLFetchService fetchService = URLFetchServiceFactory.getURLFetchService();
        final URL url = new URL(getBaseUrl() + page);
        LOG.info(url.toString());
        final HTTPResponse resp = fetchService.fetch(url);
        Assert.assertEquals(200, resp.getResponseCode());
        return new String(resp.getContent(), "UTF-8");
    }


}
