package ru.vashan.server.testinfra;

import com.google.appengine.api.urlfetch.*;
import com.google.appengine.repackaged.org.apache.http.protocol.HTTP;
import com.google.appengine.tools.development.testing.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Logger;

public class BaseServerTest {
    private static final Logger LOG = Logger.getLogger(BaseServerTest.class.getName());
    protected final LocalServiceTestHelper fetchUrl = new LocalServiceTestHelper(new LocalURLFetchServiceTestConfig(), new LocalDatastoreServiceTestConfig());

    @Before
    public void setUp() {
        fetchUrl.setUp();
    }

    @After
    public void tearDown() {
        fetchUrl.tearDown();
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

    protected String doPutAndGetContent(String page, String content, MediaType contentType) throws IOException {
        final URLFetchService fetchService = URLFetchServiceFactory.getURLFetchService();
        final URL url = new URL(getBaseUrl() + page);
        LOG.info(url.toString());
        final HTTPRequest request = new HTTPRequest(url, HTTPMethod.POST);
        request.setPayload(content.getBytes("UTF-8"));
        request.setHeader(new HTTPHeader(HTTP.CONTENT_TYPE, contentType.toString()));
        final HTTPResponse resp = fetchService.fetch(request);
        Assert.assertEquals(200, resp.getResponseCode());
        return new String(resp.getContent(), "UTF-8");
    }

    protected <T> T doPutAndGetContentJSon(String page, Object o, Class<T> returnType) throws IOException {
        final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
        return gson.fromJson(doPutAndGetContent(page, gson.toJson(o), MediaType.APPLICATION_JSON), returnType);

    }


}
