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
    public static final Set<String> DENIED_GOOGLE_ARTIFACTS = new HashSet<>(Arrays.asList("appengine-api-stubs", "appengine-tools-sdk", "appengine-testing"));
    private final static String appEngineHome;
    private final static String name;
    static {

        if(new File("vashan-war").exists())
            name = "vashan-war";
        else
            name = ".";

        try(FileInputStream stream = new FileInputStream(name + "/appengine.properties")) {
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
        return new File(name + "/src/main/webapp");
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
        for (String allowedGoogleArtifact : DENIED_GOOGLE_ARTIFACTS) {
            if(s.contains(allowedGoogleArtifact)) {
                return false;
            }
        }
        return true;
    }



}
