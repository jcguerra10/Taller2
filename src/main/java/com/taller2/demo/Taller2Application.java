package com.taller2.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.taller2.demo.model.prod.UserApp;
import com.taller2.demo.model.prod.UserType;
import com.taller2.demo.services.UserServiceImp;

@SpringBootApplication
@ComponentScan("com.taller2.demo")
public class Taller2Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext s = SpringApplication.run(Taller2Application.class, args);
		
		UserServiceImp usi = s.getBean(UserServiceImp.class);
		
		UserApp uaAdmin = new UserApp();
		
		uaAdmin.setId(1);
		uaAdmin.setUsername("admin1");
		uaAdmin.setPassword("{noop}123456");
		uaAdmin.setType(UserType.administrator);
		
		UserApp uaOper = new UserApp();
		
		uaOper.setId(2);
		uaOper.setUsername("oper1");
		uaOper.setPassword("{noop}123456");
		uaOper.setType(UserType.operator);
		
		usi.save(uaAdmin);
		usi.save(uaOper);
	}

}
