package ru.vashan.server;

import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.appengine.tools.development.testing.BaseDevAppServerTestConfig;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalURLFetchServiceTestConfig;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.net.URL;

public class BaseServerTest {
    protected final LocalServiceTestHelper testHelper = new LocalServiceTestHelper(new LocalURLFetchServiceTestConfig(), new LocalDatastoreServiceTestConfig());
    private final static Gson GSON = new Gson();

    @Before
    public void setUp() {
        testHelper.setUp();
    }

    @After
    public void tearDown() {
        testHelper.tearDown();
    }

    protected String getBaseUrl() {
        return "http://localhost:" + System.getProperty(BaseDevAppServerTestConfig.DEFAULT_PORT_SYSTEM_PROPERTY) + "/";
    }

    protected JsonObject getPageContent(String page) throws IOException {
        final URLFetchService fetchService = URLFetchServiceFactory.getURLFetchService();
        final HTTPResponse resp = fetchService.fetch(new URL(getBaseUrl() + page));
        Assert.assertEquals(200, resp.getResponseCode());
        return GSON.fromJson(new String(resp.getContent(), "UTF-8"), JsonObject.class);
    }


}
