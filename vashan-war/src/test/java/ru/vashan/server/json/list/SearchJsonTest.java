package ru.vashan.server.json.list;

import com.google.appengine.tools.development.testing.DevAppServerTest;
import com.google.appengine.tools.development.testing.DevAppServerTestRunner;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import ru.vashan.server.BaseServerTest;
import ru.vashan.server.TestConfig;

@RunWith(DevAppServerTestRunner.class)
@DevAppServerTest(TestConfig.class)
public class SearchJsonTest extends BaseServerTest {
    @Test
    public void testName() throws Exception {
        final JsonObject content = getPageContent("list/search.json");
        final JsonArray jsonArray = content.getAsJsonArray();
        Assert.assertNotNull(jsonArray);
    }

}
