package ru.vashan.repository;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.Result;
import com.googlecode.objectify.cmd.LoadType;
import com.googlecode.objectify.cmd.Loader;
import com.googlecode.objectify.cmd.Saver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.vashan.domain.BuyList;
import ru.vashan.repository.buylist.BuyListRepository;
import ru.vashan.repository.buylist.BuyListRepositoryImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class BuyListRepositoryImplTest {

    private Objectify ojy;
    private Loader loader;
    private Saver saver;

    @Configuration
    static class ContextConfiguration {
        @Bean
        public BuyListRepository buyListRepository() {
            return new BuyListRepositoryImpl();
        }

        @Bean
        public ObjectifyFactory objectifyFactory() {
            return mock(ObjectifyFactory.class);
        }
    }

    @Autowired
    private BuyListRepository buyListRepository;

    @Autowired
    private ObjectifyFactory objectifyFactory;

    @Before
    public void setUp() throws Exception {
        reset(objectifyFactory);

        ojy = mock(Objectify.class);
        when(objectifyFactory.begin()).thenReturn(ojy);
        loader = mock(Loader.class);
        when(ojy.load()).thenReturn(loader);
        saver = mock(Saver.class);
        when(ojy.save()).thenReturn(saver);
    }

    @Test
    public void testGetAll() throws Exception {
        final LoadType<BuyList> loadType = mock(LoadType.class);
        when(loader.type(BuyList.class)).thenReturn(loadType);
        final BuyList buyList1 = mock(BuyList.class);
        final BuyList buyList2 = mock(BuyList.class);
        when(loadType.list()).thenReturn(Arrays.asList(
                buyList1,
                buyList2
        ));
        final List<BuyList> all = buyListRepository.getAll();
        assertEquals(2, all.size());
        assertEquals(buyList1, all.get(0));
        assertEquals(buyList2, all.get(1));
    }

    @Test
    public void testSave() throws Exception {
        final BuyList input = mock(BuyList.class);
        final Result result = mock(Result.class);
        when(saver.entity(input)).thenReturn(result);
        buyListRepository.save(input);
        verify(saver).entity(input);
        verify(result).now();


    }
}
