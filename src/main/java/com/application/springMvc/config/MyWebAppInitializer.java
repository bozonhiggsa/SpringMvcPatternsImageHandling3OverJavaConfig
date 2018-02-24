package com.application.springMvc.config;

import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * Java config instead of web.xml
 * @author Ihor Savchenko
 * @version 1.0
 */
public class MyWebAppInitializer extends
        AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    /*protected Filter[] getServletFilters() {
        return new Filter[]{new ShallowEtagHeaderFilter()};
    }*/
}
