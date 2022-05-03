package com.taller1.demo.mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.taller2.demo.model.prod.Location;
import com.taller2.demo.model.prod.Product;
import com.taller2.demo.model.prod.Productcategory;
import com.taller2.demo.model.prod.Productsubcategory;
import com.taller2.demo.repositories.ProductRepository;
import com.taller2.demo.services.ProductServiceImp;

class ProductTest {

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ProductServiceImp ps;

	private Product product0;
	private Optional<Product> product0op;

	private Product product1;
	private Product product2;
	private Product product3;
	private Product product4;
	

	// <------------------------> Setups <------------------------>
	
	@BeforeEach
	void setUp1() {
		MockitoAnnotations.openMocks(this);

		Productcategory pCategory = new Productcategory();
		Productsubcategory pSubCategory = new Productsubcategory();
		pSubCategory.setProductcategory(pCategory);
		product0 = new Product();
		product0.setProductsubcategory(pSubCategory);
		product0.setProductnumber("1");
		product0.setSellstartdate(LocalDate.of(2022, 3, 15));
		product0.setSellenddate(LocalDate.of(2022, 3, 16)); //
		product0.setWeight(BigDecimal.valueOf(12));
		product0.setSize(BigDecimal.valueOf(2));
	}
	
	@BeforeEach
	void setUp2() {
		MockitoAnnotations.openMocks(this);

		Productcategory pCategory = new Productcategory();
		Productsubcategory pSubCategory = new Productsubcategory();
		pSubCategory.setProductcategory(pCategory);
		product2 = new Product();
		product2.setProductsubcategory(pSubCategory);
		product2.setProductnumber("1");
		product2.setSellstartdate(LocalDate.of(2022, 3, 14));
		product2.setSellenddate(LocalDate.of(2022, 3, 12)); //
		product2.setWeight(BigDecimal.valueOf(-1));
		product2.setSize(BigDecimal.valueOf(-1));
	}
	
	@BeforeEach
	void setUp3() {
		MockitoAnnotations.openMocks(this);

		product4 = new Product();
		product4.setProductid(1);
	}
	
	@BeforeEach
	void setUp4() {
		MockitoAnnotations.openMocks(this);
		
		product0op = Optional.of(product0);
		
		Productcategory pCategory = new Productcategory();
		Productsubcategory pSubCategory = new Productsubcategory();
		pSubCategory.setProductcategory(pCategory);
		product1 = new Product();
		product1.setProductsubcategory(pSubCategory);
		product1.setProductnumber("1");
		product1.setSellstartdate(LocalDate.of(2022, 3, 14));
		product1.setSellenddate(LocalDate.of(2022, 3, 15)); //
		product1.setWeight(BigDecimal.valueOf(12));
		product1.setSize(BigDecimal.valueOf(1));
	}
	
	@BeforeEach
	void setUp5() {
		MockitoAnnotations.openMocks(this);
		
		product0op = Optional.of(product0);
		
		Productcategory pCategory = new Productcategory();
		Productsubcategory pSubCategory = new Productsubcategory();
		pSubCategory.setProductcategory(pCategory);
		product3 = new Product();
		product3.setProductsubcategory(pSubCategory);
		product3.setProductnumber("1");
		product3.setSellstartdate(LocalDate.of(2022, 3, 14));
		product3.setSellenddate(LocalDate.of(2022, 3, 12)); //
		product3.setWeight(BigDecimal.valueOf(-1));
		product3.setSize(BigDecimal.valueOf(-1));
	}

	// <------------------------> Save <------------------------>
	
	@Test
	void testThatSave() {
		when(productRepository.save(product0)).thenReturn(product0);
		assertNotNull(ps.saveProduct(product0));
	}


	@Test
	void testConstraints() {
		when(productRepository.save(product0)).thenReturn(product0);
		Product testLoc = ps.saveProduct(product0);
		assertNotNull(testLoc.getProductsubcategory());
		assertNotNull(testLoc.getProductsubcategory().getProductcategory());
		assertTrue(testLoc.getProductnumber().equals("1"), "ProductNumber");
		assertTrue(testLoc.getSellstartdate().compareTo(testLoc.getSellenddate()) < 0 , "Date");
		assertTrue(testLoc.getWeight().doubleValue() > 0, "Weight");
		assertTrue(testLoc.getSize().intValue() > 0, "Size");
	}

	// <------------------------> Save Throws <------------------------>
	
	@Test
	void testExceptionSave() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ps.saveProduct(product2);
		});
		
		product2.setSellstartdate(LocalDate.of(2022, 3, 18));
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ps.saveProduct(product2);
		});
		
		product2.setWeight(BigDecimal.valueOf(12));
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ps.saveProduct(product2);
		});
	}

	// <------------------------> Empty Save <------------------------>
	
	@Test
	void testExceptionEmpty() {
		Assertions.assertThrows(NullPointerException.class, () -> {
			ps.saveProduct(product4);
		});
		
		Productsubcategory pSubCategory = new Productsubcategory();
		product4.setProductsubcategory(pSubCategory);
		
		Assertions.assertThrows(NullPointerException.class, () -> {
			ps.saveProduct(product4);
		});
		
		Productcategory pCategory = new Productcategory();
		product4.getProductsubcategory().setProductcategory(pCategory);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ps.saveProduct(product4);
		});
		
		product4.setProductnumber("1");
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ps.saveProduct(product4);
		});
		
		product4.setSellstartdate(LocalDate.of(2022, 3, 14));
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ps.saveProduct(product4);
		});
		
		product4.setSellenddate(LocalDate.of(2022, 3, 18));
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ps.saveProduct(product4);
		});
		
		product4.setWeight(BigDecimal.valueOf(12));
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ps.saveProduct(product4);
		});
	}

	// <------------------------> Edit <------------------------>
	

	@Test
	void testThatEdit() {
		when(productRepository.findById(1)).thenReturn(product0op);
		when(productRepository.save(product0)).thenReturn(product0);
		Product test = ps.editProduct(product1, 1);
		assertEquals(test.getProductid(), product1.getProductid());
		assertEquals(test.getProductsubcategory(), product1.getProductsubcategory());
		assertEquals(test.getProductsubcategory().getProductcategory(), product1.getProductsubcategory().getProductcategory());
		assertEquals(test.getSellstartdate(), product1.getSellstartdate());
		assertEquals(test.getSellenddate(), product1.getSellenddate());
		assertEquals(test.getWeight(), product1.getWeight());
		assertEquals(test.getSize(), product1.getSize());
	}

	// <------------------------> Edit Throws <------------------------>
	
	@Test
	void testExceptionEdit() {
		when(productRepository.findById(1)).thenReturn(product0op);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ps.editProduct(product3, 1);
		});
		
		product3.setProductnumber("1");
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ps.editProduct(product3, 1);
		});
		
		product3.setSellstartdate(LocalDate.of(2022, 3, 14));
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ps.editProduct(product3, 1);
		});
		
		product3.setSellenddate(LocalDate.of(2022, 3, 15));
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ps.editProduct(product3, 1);
		});
		
		product3.setWeight(BigDecimal.valueOf(12));
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ps.editProduct(product3, 1);
		});
	}

	// <------------------------> Empty Edit <------------------------>
	
	
	@Test
	void testExceptionEditEmpty() {
		when(productRepository.findById(1)).thenReturn(product0op);
		
		Assertions.assertThrows(NullPointerException.class, () -> {
			ps.editProduct(product4, 1);
		});
		
		Productsubcategory pSubCategory = new Productsubcategory();
		product4.setProductsubcategory(pSubCategory);
		
		Assertions.assertThrows(NullPointerException.class, () -> {
			ps.editProduct(product4, 1);
		});
		
		Productcategory pCategory = new Productcategory();
		product4.getProductsubcategory().setProductcategory(pCategory);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ps.editProduct(product4, 1);
		});
		
		product4.setProductnumber("1");
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ps.editProduct(product4, 1);
		});
		
		product4.setSellstartdate(LocalDate.of(2022, 3, 14));
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ps.editProduct(product4, 1);
		});
		
		product4.setSellenddate(LocalDate.of(2022, 3, 15));
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ps.editProduct(product4, 1);
		});
		
		product4.setWeight(BigDecimal.valueOf(12));
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ps.editProduct(product4, 1);
		});
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
