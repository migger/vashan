package ru.vashan.server;

import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.appengine.tools.development.testing.*;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URL;

@RunWith(DevAppServerTestRunner.class)
@DevAppServerTest(TestConfig.class)
public class ServerStartedTest extends BaseServerTest{
    @Test
    public void testEndToEnd() throws Exception {
        final URLFetchService fetchService = URLFetchServiceFactory.getURLFetchService();
        final HTTPResponse resp = fetchService.fetch(new URL(getBaseUrl()));
        Assert.assertEquals(200, resp.getResponseCode());
    }
}