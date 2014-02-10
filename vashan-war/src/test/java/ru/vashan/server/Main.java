package ru.vashan.server;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.appengine.api.urlfetch.URLFetchServicePb;
import com.google.appengine.tools.development.testing.*;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@RunWith(DevAppServerTestRunner.class)
@DevAppServerTest(Main.TestConfig.class)
public class Main {
    private static final int PORT = 8080;
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
            return Arrays.asList(((URLClassLoader)Main.class.getClassLoader()).getURLs());
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
        URLFetchService fetchService = URLFetchServiceFactory.getURLFetchService();
        HTTPResponse resp = fetchService.fetch(new URL("http://localhost:" + PORT + "/"));
        Assert.assertEquals(200, resp.getResponseCode());
    }
}