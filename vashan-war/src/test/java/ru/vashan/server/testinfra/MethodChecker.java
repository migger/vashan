package ru.vashan.server.testinfra;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class MethodChecker {
    public void checkMethod(Method method) {

    }

    public void checkParameter(Parameter parameter) {
        switch (parameter.getIndex()) {
            case 0:
                checkParameter1(parameter);
                break;
            case 1:
                checkParameter2(parameter);
                break;
            case 2:
                checkParameter3(parameter);
                break;
            case 3:
                checkParameter4(parameter);
                break;
            case 4:
                checkParameter5(parameter);
                break;
            case 5:
                checkParameter6(parameter);
                break;
        }
    }

    protected void checkParameter1(Parameter parameter) {

    }

    protected void checkParameter2(Parameter parameter) {

    }

    protected void checkParameter3(Parameter parameter) {

    }

    protected void checkParameter4(Parameter parameter) {

    }

    protected void checkParameter5(Parameter parameter) {

    }

    protected void checkParameter6(Parameter parameter) {

    }

    protected static class Parameter {
        int index;
        Annotation[] annotations;

        public Parameter(int index, Annotation[] annotations) {
            this.index = index;
            this.annotations = annotations;
        }

        public <T extends Annotation> T getAnnotation(Class<T> annoClass){
            for (Annotation annotation : annotations) {
                if (annoClass.isInstance(annotation))
                    return annoClass.cast(annotation);
            }
            return null;
        }

        public int getIndex() {
            return index;
        }
    }
}