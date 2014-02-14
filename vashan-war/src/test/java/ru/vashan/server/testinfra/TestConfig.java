package ru.vashan.server.testinfra;

import com.google.appengine.tools.development.testing.BaseDevAppServerTestConfig;
import ru.vashan.server.ServerStartedTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class TestConfig extends BaseDevAppServerTestConfig {
    public static final Set<String> ALLOWED_GOOGLE_ARTIFACTS = new HashSet<>(Arrays.asList("gson", "appengine-api-1.0-sdk"));
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
        for (URL o : ((URLClassLoader)ServerStartedTest.class.getClassLoader()).getURLs()) {
            final String s = o.toString();
            if(!s.contains("google") || isAllowed(s)) {
                urls.add(o);
            }
        }
        return urls;
    }

    private boolean isAllowed(String s) {
        for (String allowedGoogleArtifact : ALLOWED_GOOGLE_ARTIFACTS) {
            if(s.contains(allowedGoogleArtifact)) {
                return true;
            }
        }
        return false;
    }

}
