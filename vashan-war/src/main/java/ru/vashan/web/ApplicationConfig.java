package ru.vashan.web;

import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.spring.ObjectifyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import ru.vashan._Components;
import ru.vashan.domain.BuyList;
import ru.vashan.web.controllers.Excluded;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Configuration
@EnableWebMvc()
@ComponentScan(
basePackageClasses = {
        _Components.class
},
excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Excluded.class)
})
public class ApplicationConfig extends WebMvcConfigurerAdapter {
    @Bean
    public ObjectifyFactoryBean objectifyFactoryBean() {
        final ObjectifyFactoryBean bean = new ObjectifyFactoryBean();
        bean.setClasses(Arrays.<Class<?>>asList(
                BuyList.class
        ));
        return bean;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.getObjectMapper().setDateFormat(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS zzz", Locale.US));
        converters.add(converter);
    }

}