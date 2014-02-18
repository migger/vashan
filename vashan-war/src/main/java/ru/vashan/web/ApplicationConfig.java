package ru.vashan.web;

import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.spring.ObjectifyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import ru.vashan._Components;
import ru.vashan.domain.BuyList;

import java.util.Arrays;

@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = {
        _Components.class
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

}