package com.main;

import org.springframework.beans.factory.BeanFactory;

public class Main {
    public static void main(String[] args) throws Exception{
        BeanFactory beanFactory = new BeanFactory();
        beanFactory.instantiate("com.main");
        beanFactory.populateProperties();
        beanFactory.injectBeanNames();

        ProductService productService = (ProductService) beanFactory.getBean("productService");
        System.out.println(productService);

        PromotionsService promotionsService = (PromotionsService) beanFactory.getBean("promotionsService");
        System.out.println("Bean name = " + promotionsService.getBeanName());

        OrderService orderService = (OrderService) beanFactory.getBean("orderService");
        System.out.println("Bean factory = " + orderService.getBeanFactory());
        System.out.println(orderService);

        PromotionsService promotionsService1 = productService.getPromotionsService();
        System.out.println(promotionsService1);

        OrderService orderService1 = productService.getOrderService();
        System.out.println(orderService1);
    }
}