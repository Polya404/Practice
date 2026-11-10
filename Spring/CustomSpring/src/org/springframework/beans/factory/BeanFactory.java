package org.springframework.beans.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.stereotype.Component;
import org.springframework.beans.factory.stereotype.Service;

import javax.annotations.Resource;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BeanFactory {
    private final Map<String, Object> singletons = new HashMap<>();

    public Object getBean(String beanName) {
        return singletons.get(beanName);
    }

    public void instantiate(String basePackage) {
        try {
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            String path = basePackage.replace('.', '/');
            Enumeration<URL> resources = classLoader.getResources(path);
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                File file = new File(resource.toURI());
                for (File classFile : Objects.requireNonNull(file.listFiles())) {
                    getFileStructure(classFile, basePackage);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void getFileStructure(File file, String basePackage) {
        if (file.isFile()) {
            try {
                annotationScanning(file, basePackage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            for (File f : Objects.requireNonNull(file.listFiles())) {
                getFileStructure(f, basePackage);
            }
        }
    }

    private void annotationScanning(File file, String basePackage) throws Exception {
        String fileName = file.getName();
        System.out.println(fileName);
        if (fileName.endsWith(".class")) {
            String className = fileName.substring(0, fileName.lastIndexOf("."));
            Class<?> classObject = Class.forName(basePackage + "." + className);
            if (classObject.isAnnotationPresent(Component.class) || classObject.isAnnotationPresent(Service.class)) {
                System.out.println("Component: " + classObject);
                creatingAndAddingObjectToContainer(className, classObject);
            }
        }
    }

    private void creatingAndAddingObjectToContainer(String className, Class<?> classObject) throws Exception {
        Object instance = classObject.getDeclaredConstructor().newInstance();
        String beanName = className.substring(0, 1).toLowerCase() + className.substring(1);
        singletons.put(beanName, instance);
    }

    public void populateProperties() throws Exception {
        System.out.println("==populateProperties==");
        for (Object object : singletons.values()) {
            for (Field field : object.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    injectionByType(object, field);
                }
                if (field.isAnnotationPresent(Resource.class)) {
                    injectionByName(object, field);
                }
            }
        }
    }

    private void injectionByName(Object object, Field field) throws Exception {
        for (Object dependency : singletons.values()) {
            if (dependency.getClass().getSimpleName().equals(fieldNameWithUpCase(field))) {
                String setterName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                System.out.println("Setter name = " + setterName);
                Method setter = object.getClass().getMethod(setterName, dependency.getClass());
                setter.invoke(object, dependency);
            }
        }

    }

    private void injectionByType(Object object, Field field) throws Exception {
        for (Object dependency : singletons.values()) {
            if (dependency.getClass().equals(field.getType())) {
                String setterName = "set" + fieldNameWithUpCase(field);
                System.out.println("Setter name = " + setterName);
                Method setter = object.getClass().getMethod(setterName, dependency.getClass());
                setter.invoke(object, dependency);
            }
        }
    }

    private String fieldNameWithUpCase(Field field) {
        return field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
    }

    public void injectBeanNames() {
        for (String name : singletons.keySet()) {
            Object bean = singletons.get(name);
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(name);
            }
        }
    }

    public void injectBeanFactory(){
        // TODO
    }
}