package org.application;

import org.application.aspect.SimpleService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringAopApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringAopApplication.class, args);
		SimpleService simpleService = context.getBean(SimpleService.class);
		simpleService.callPrint();
//		simpleService.print();
//		simpleService.print2();
	}

}
