package ru.vashan.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import ru.vashan.web.controllers.list._ListControllers;

@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = {
        ApplicationConfig.class,
        _ListControllers.class
})
public class ApplicationConfig extends WebMvcConfigurerAdapter {

}