package com.taller1.demo.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.taller2.demo.Taller2Application;
import com.taller2.demo.model.prod.Product;
import com.taller2.demo.model.prod.Productcategory;
import com.taller2.demo.model.prod.Productcosthistory;
import com.taller2.demo.model.prod.ProductcosthistoryPK;
import com.taller2.demo.model.prod.Productsubcategory;
import com.taller2.demo.repositories.ProductRepository;
import com.taller2.demo.repositories.ProductcosthistoryRepository;
import com.taller2.demo.services.ProductServiceImp;
import com.taller2.demo.services.ProductcategoryServiceImp;
import com.taller2.demo.services.ProductcosthistoryServiceImp;
import com.taller2.demo.services.ProductsubcategoryImp;


@ContextConfiguration(classes=Taller2Application.class)
@ExtendWith(SpringExtension.class)
class ProductcosthistoryTest {

	private ProductcosthistoryRepository pchRepository;
	private ProductRepository procRepository;

	private ProductcosthistoryServiceImp pchService;
	private ProductServiceImp ps;
	private ProductsubcategoryImp pscService;
	private ProductcategoryServiceImp pcService;

	private Productcosthistory pch0;
	private Optional<Productcosthistory> pch0op;
	
	private Product proc;
	
	private Productcosthistory pch1;
	private Productcosthistory pch2;
	private Productcosthistory pch3;
	private Productcosthistory pch4;

	// <------------------------> Setups <------------------------>
	
	@Autowired
	public ProductcosthistoryTest(ProductcosthistoryRepository pchRepository, ProductRepository procRepository,
			ProductcosthistoryServiceImp pchService, ProductServiceImp ps, ProductsubcategoryImp pscService,
			ProductcategoryServiceImp pcService) {
		super();
		this.pchRepository = pchRepository;
		this.procRepository = procRepository;
		this.pchService = pchService;
		this.ps = ps;
		this.pscService = pscService;
		this.pcService = pcService;
	}
	
	@BeforeEach
	void setUp1() {
		proc = new Product();
		proc.setProductid(1);
		Productcategory pCategory = new Productcategory();
		
		
		
		Productsubcategory pSubCategory = new Productsubcategory();
		
		pSubCategory.setProductcategory(pCategory);
		
		proc.setProductsubcategory(pSubCategory);
		
		pcService.saveProductcategory(pCategory);
		pscService.saveProductsubcategory(pSubCategory);
		
		proc.setProductnumber("1");
		proc.setSellstartdate(Timestamp.valueOf("2022-03-12 10:30:04"));
		proc.setSellenddate(Timestamp.valueOf("2022-03-13 10:30:04")); //
		proc.setWeight(BigDecimal.valueOf(12));
		proc.setSize(BigDecimal.valueOf(2));
		
		ps.saveProduct(proc);
	}
	

	@BeforeEach
	void setUp2() {
		pch0 = new Productcosthistory();
		ProductcosthistoryPK pk = new ProductcosthistoryPK();
		pk.setProductid(1);
		pk.setStartdate(Timestamp.valueOf("2022-03-12 10:30:04"));
		pch0.setId(pk);
		pch0.setProduct(proc);
		pch0.setEnddate(Timestamp.valueOf("2022-03-10 10:05:23"));
		pch0.setStandardcost(BigDecimal.valueOf(25000));
		pch0.setModifieddate(Timestamp.valueOf("2022-03-20 10:05:23"));
	}

	@BeforeEach
	void setUp3() {
		pch2 = new Productcosthistory();
		ProductcosthistoryPK pk = new ProductcosthistoryPK();
		pk.setProductid(1);
		pk.setStartdate(Timestamp.valueOf("2022-03-12 10:30:04"));
		pch2.setId(pk);
		pch2.setProduct(procRepository.findById(1).get());
		pch2.setEnddate(Timestamp.valueOf("2024-03-10 10:05:23"));
		pch2.setStandardcost(BigDecimal.valueOf(-2));
	}
	
	@BeforeEach
	void setUp4() {
		pch4 = new Productcosthistory();
	}
	
	@BeforeEach
	void setUp5() {
		pch0op = Optional.of(pch0);
		
		pch1 = new Productcosthistory();
		ProductcosthistoryPK pk = new ProductcosthistoryPK();
		pk.setProductid(1);
		pk.setStartdate(Timestamp.valueOf("2022-03-12 10:30:04"));
		pch1.setId(pk);
		pch1.setProduct(procRepository.findById(1).get());
		pch1.setEnddate(Timestamp.valueOf("2022-03-10 12:12:23"));
		pch1.setStandardcost(BigDecimal.valueOf(23500));
	}
	
	@BeforeEach
	void setUp6() {
		pch3 = new Productcosthistory();
		ProductcosthistoryPK pk = new ProductcosthistoryPK();
		pk.setProductid(1);
		pk.setStartdate(Timestamp.valueOf("2022-03-12 10:30:04"));
		pch3.setId(pk);
		pch3.setProduct(procRepository.findById(1).get());
		pch3.setEnddate(Timestamp.valueOf("2024-03-10 10:05:23"));
		pch3.setStandardcost(BigDecimal.valueOf(-2));
	}

	// <------------------------> Save <------------------------>
	
	@Test
	void testThatSave() {
		assertNotNull(pchService.saveProductcosthistory(pch0));
	}
	
	@Test
	void testConstraints() {
		Productcosthistory test = pchService.saveProductcosthistory(pch0);
		
		assertNotNull(test.getProduct());
		assertTrue(test.getEnddate().compareTo(new Timestamp(System.currentTimeMillis())) < 0);
		assertTrue(test.getStandardcost().doubleValue() >= 0);
	}
	
	// <------------------------> Save Throws <------------------------>
	
	
	@Test
	void testExceptionSave() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			pchService.saveProductcosthistory(pch2);
		});
		
		pch2.setEnddate(Timestamp.valueOf("2022-03-10 10:05:23"));
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			pchService.saveProductcosthistory(pch2);
		});
	}
	
	// <------------------------> Empty Save <------------------------>
	
	@Test
	void testExceptionEmpty() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			pchService.saveProductcosthistory(pch2);
		});
		
		Product proc = new Product();
		proc.setProductid(1);
		pch2.setProduct(proc);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			pchService.saveProductcosthistory(pch2);
		});
		
		pch2.setEnddate(Timestamp.valueOf("2022-03-10 10:05:23"));
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			pchService.saveProductcosthistory(pch2);
		});
	}
	
	
	// <------------------------> Edit <------------------------>
	
	@Test
	void testThatEdit() {
		pchService.saveProductcosthistory(pch0);
		Productcosthistory test = pchService.editProductcosthistory(pch1, pch0.getId());
		assertEquals(test.getId(), pch1.getId());
		assertEquals(test.getProduct(), pch1.getProduct());
		assertEquals(test.getEnddate(), pch1.getEnddate());
		assertEquals(test.getStandardcost(), pch1.getStandardcost());
	}
	
	
	// <------------------------> Edit Throws <------------------------>
	
	@Test
	void testExceptionEdit() {
		pchService.saveProductcosthistory(pch0);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			pchService.editProductcosthistory(pch3, pch0.getId());
		});
		
		pch3.setEnddate(Timestamp.valueOf("2022-03-10 10:05:23"));
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			pchService.editProductcosthistory(pch3, pch0.getId());
		});
	}
	
	// <------------------------> Empty Edit <------------------------>
	
	@Test
	void testExceptionEditEmpty() {
		pchService.saveProductcosthistory(pch0);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			pchService.editProductcosthistory(pch4, pch0.getId());
		});
		
		Product proc = new Product();
		proc.setProductid(1);
		pch2.setProduct(proc);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			pchService.editProductcosthistory(pch4, pch0.getId());
		});
		
		pch4.setEnddate(Timestamp.valueOf("2022-03-10 10:05:23"));
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			pchService.editProductcosthistory(pch4, pch0.getId());
		});
	}
	
}
