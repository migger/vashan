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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@RunWith(DevAppServerTestRunner.class)
@DevAppServerTest(End2EndTest.TestConfig.class)
public class End2EndTest {
    private final LocalServiceTestHelper testHelper = new LocalServiceTestHelper(new LocalURLFetchServiceTestConfig(), new LocalDatastoreServiceTestConfig());

    public static class TestConfig extends BaseDevAppServerTestConfig {
        private final static String appEngineHome;
        static {
           try(FileInputStream stream = new FileInputStream("vashan-war/appengine.properties")) {
               final Properties x = new Properties();
               x.load(stream);
               appEngineHome = x.getProperty("appengine.home");
               if(appEngineHome == null) {
                   throw new RuntimeException("appengine.home not found");
               }
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
        }
        @Override
        public File getSdkRoot() {
            return new File(appEngineHome);
        }

        @Override
        public File getAppDir() {
            return new File("vashan-war/src/main/webapp");
        }

        @Override
        public List<URL> getClasspath() {
            final ArrayList<URL> urls = new ArrayList<>();
            for (URL o : ((URLClassLoader)End2EndTest.class.getClassLoader()).getURLs()) {
                final String s = o.toString();
                if(!s.contains("google") || s.contains("appengine-api-1.0-sdk")) {
                    urls.add(o);
                }
            }
            return urls;
        }

    }

    @Before
    public void setUpHelper() {
        testHelper.setUp();
    }

    @After
    public void tearDownHelper() {
        testHelper.tearDown();
    }

    @Test
    public void testEndToEnd() throws Exception {
        final URLFetchService fetchService = URLFetchServiceFactory.getURLFetchService();
        final HTTPResponse resp = fetchService.fetch(new URL("http://localhost:" + System.getProperty(BaseDevAppServerTestConfig.DEFAULT_PORT_SYSTEM_PROPERTY) + "/"));
        Assert.assertEquals(200, resp.getResponseCode());
    }
}