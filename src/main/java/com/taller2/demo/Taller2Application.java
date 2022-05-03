package com.taller2.demo;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.taller2.demo.model.prod.Location;
import com.taller2.demo.model.prod.Product;
import com.taller2.demo.model.prod.Productcategory;
import com.taller2.demo.model.prod.Productcosthistory;
import com.taller2.demo.model.prod.Productsubcategory;
import com.taller2.demo.model.prod.UserApp;
import com.taller2.demo.model.prod.UserType;
import com.taller2.demo.repositories.LocationRepository;
import com.taller2.demo.repositories.ProductRepository;
import com.taller2.demo.repositories.ProductcategoryRepository;
import com.taller2.demo.repositories.ProductcosthistoryRepository;
import com.taller2.demo.repositories.ProductsubcategoryRepository;
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
		
		ProductRepository pr = s.getBean(ProductRepository.class);
		ProductcategoryRepository pcr = s.getBean(ProductcategoryRepository.class);
		ProductsubcategoryRepository pscr = s.getBean(ProductsubcategoryRepository.class);
		LocationRepository lr = s.getBean(LocationRepository.class);
		
		Productcategory pCategory = new Productcategory();
		pCategory.setName("Tech");
		
		Productsubcategory pSubCategory = new Productsubcategory();
		pSubCategory.setName("Iphone");
		pSubCategory.setProductcategory(pCategory);
		
		Product p = new Product();
		p.setName("iphone");
		p.setProductnumber("21");
		p.setSellstartdate(LocalDate.of(2022, 3, 14));
		p.setSellenddate(LocalDate.of(2022, 3, 15));
		p.setProductsubcategory(pSubCategory);
		p.setSize(BigDecimal.valueOf(12));
		p.setWeight(BigDecimal.valueOf(12));
		
		pcr.save(pCategory);
		pscr.save(pSubCategory);
		
		pr.save(p);
		
		Location l = new Location();
		
		l.setLocationid(1);
		l.setName("stan1");
		l.setAvailability(BigDecimal.valueOf(2));
		l.setCostrate(BigDecimal.valueOf(1));
		
		lr.save(l);
		
		ProductcosthistoryRepository pchr = s.getBean(ProductcosthistoryRepository.class);
		
		Productcosthistory pch = new Productcosthistory();
		
		pch.setProduct(p);
		pch.setStartdate(LocalDate.of(2022, 4, 13));
		pch.setEnddate(LocalDate.of(2022, 4, 14));
		pch.setStandardcost(BigDecimal.valueOf(12));
		
		pchr.save(pch);
	}

}
