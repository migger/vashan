package ru.vashan.domain;

import com.googlecode.objectify.annotation.OnSave;
import junit.framework.Assert;
import org.junit.Test;

import javax.persistence.PrePersist;
import java.util.Arrays;
import java.util.HashSet;

public class ItemTest {

    @Test
    public void testBeforePersist() throws Exception {
        Assert.assertNotNull(Item.class.getMethod("buildItemIndex").getAnnotation(OnSave.class));
    }

    @Test
    public void testTestEmpty() throws Exception {
        final Item item = new Item("");
        item.buildItemIndex();
        Assert.assertEquals(0, item.getTitleIndex().length);
        Assert.assertEquals("", item.getTitleLowerCase());
    }

    @Test
    public void testTestNull() throws Exception {
        final Item item = new Item();
        item.buildItemIndex();
        Assert.assertEquals(0, item.getTitleIndex().length);
        Assert.assertNull(item.getTitleLowerCase());
    }

    @Test
    public void testLowerCase() throws Exception {
        final Item item = new Item("AbCd");
        item.buildItemIndex();
        Assert.assertEquals("abcd", item.getTitleLowerCase());
    }

    @Test
    public void testTest1() throws Exception {
        final Item item = new Item("1");
        item.buildItemIndex();
        Assert.assertEquals(1, item.getTitleIndex().length);
        Assert.assertEquals("1", item.getTitleIndex()[0]);
    }

    @Test
    public void testTest2() throws Exception {
        final Item item = new Item("12");
        item.buildItemIndex();
        Assert.assertEquals(3, item.getTitleIndex().length);
        final HashSet<String> strings = new HashSet<>(Arrays.asList(item.getTitleIndex()));
        Assert.assertTrue(strings.contains("1"));
        Assert.assertTrue(strings.contains("2"));
        Assert.assertTrue(strings.contains("12"));
    }

    @Test
    public void testTest99() throws Exception {
        final char[] chars = new char[99];
        Arrays.fill(chars, 'X');
        final String title = new String(chars);
        final Item item = new Item(title);
        item.buildItemIndex();
        Assert.assertEquals(99, item.getTitleIndex().length);
        Assert.assertTrue(Arrays.asList(item.getTitleIndex()).contains("x"));
        Assert.assertFalse(Arrays.asList(item.getTitleIndex()).contains("X"));
    }

    @Test(expected = RuntimeException.class)
    public void testTest5000() throws Exception {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 56; i++){
            stringBuilder.append(i);
        }
        final Item item = new Item(stringBuilder.toString());
        item.buildItemIndex();
    }
}
