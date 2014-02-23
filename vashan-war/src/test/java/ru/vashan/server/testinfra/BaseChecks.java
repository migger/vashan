package ru.vashan.server.testinfra;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BaseChecks {
    public static void assertIsAComponent(Class<?> aClass) {
        Assert.assertNotNull(aClass.getAnnotation(Component.class));
    }

    public static void assertIsAController(String path, Class<?> aClass) throws Exception {
        Assert.assertNotNull(aClass.getAnnotation(Controller.class));
        final RequestMapping requestMapping = aClass.getAnnotation(RequestMapping.class);
        Assert.assertNotNull(requestMapping);
        Assert.assertEquals(path, requestMapping.value()[0]);
    }

    public static void ckeckMethod(Class<?> aClass, Class[] parameters, String methodName, MethodChecker methodChecker) throws NoSuchMethodException {
        final Method method = aClass.getMethod(methodName, parameters);
        Assert.assertNotNull("No method " + methodName + " in " + aClass.getName(), method);
        methodChecker.checkMethod(method);
        final Annotation[][] annotations = method.getParameterAnnotations();
        for (int i = 0; i < annotations.length; i++) {
            methodChecker.checkParameter(new MethodChecker.Parameter(i, annotations[i]));
        }
    }



}
