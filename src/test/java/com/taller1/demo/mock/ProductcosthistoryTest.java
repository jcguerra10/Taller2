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

import com.taller2.demo.model.prod.Product;
import com.taller2.demo.model.prod.Productcosthistory;
import com.taller2.demo.repositories.ProductcosthistoryRepository;
import com.taller2.demo.services.ProductcosthistoryServiceImp;

class ProductcosthistoryTest {

	@Mock
	private ProductcosthistoryRepository pchRepository;

	@InjectMocks
	private ProductcosthistoryServiceImp pchService;

	private Productcosthistory pch0;
	private Optional<Productcosthistory> pch0op;
	
	private Productcosthistory pch1;
	private Productcosthistory pch2;
	private Productcosthistory pch3;
	private Productcosthistory pch4;

	// <------------------------> Setups <------------------------>
	
	@BeforeEach
	void setUp1() {
		MockitoAnnotations.openMocks(this);
		
		Product proc = new Product();
		proc.setProductid(1);
		pch0 = new Productcosthistory();
		pch0.setProduct(proc);
		pch0.setEnddate(LocalDate.of(2022, 3, 15));
		pch0.setStandardcost(BigDecimal.valueOf(25000));
	}
	
	@BeforeEach
	void setUp2() {
		MockitoAnnotations.openMocks(this);
		
		Product proc = new Product();
		proc.setProductid(1);
		pch2 = new Productcosthistory();
		pch2.setProduct(proc);
		pch2.setEnddate(LocalDate.of(2022, 3, 14));
		pch2.setStandardcost(BigDecimal.valueOf(-2));
	}
	
	@BeforeEach
	void setUp3() {
		MockitoAnnotations.openMocks(this);
		pch4 = new Productcosthistory();
	}
	
	@BeforeEach
	void setUp4() {
		MockitoAnnotations.openMocks(this);
		
		pch0op = Optional.of(pch0);
		
		Product proc = new Product();
		proc.setProductid(1);
		pch1 = new Productcosthistory();
		pch1.setProduct(proc);
		pch1.setEnddate(LocalDate.of(2022, 3, 14));
		pch1.setStandardcost(BigDecimal.valueOf(23500));
	}
	
	@BeforeEach
	void setUp5() {
		MockitoAnnotations.openMocks(this);
		
		Product proc = new Product();
		proc.setProductid(1);
		pch3 = new Productcosthistory();
		pch3.setProduct(proc);
		pch3.setEnddate(LocalDate.of(2022, 3, 14));
		pch3.setStandardcost(BigDecimal.valueOf(-2));
	}

	// <------------------------> Save <------------------------>
	
	@Test
	void testThatSave() {
		when(pchRepository.save(pch0)).thenReturn(pch0);
		assertNotNull(pchService.saveProductcosthistory(pch0));
	}
	
	@Test
	void testConstraints() {
		when(pchRepository.save(pch0)).thenReturn(pch0);
		Productcosthistory test = pchService.saveProductcosthistory(pch0);
		
		assertNotNull(test.getProduct());
		assertTrue(test.getEnddate().isAfter(LocalDate.of(2022, 3, 14)));
		assertTrue(test.getStandardcost().doubleValue() >= 0);
	}
	
	// <------------------------> Save Throws <------------------------>
	
	
	@Test
	void testExceptionSave() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			pchService.saveProductcosthistory(pch2);
		});
		
		pch2.setEnddate(LocalDate.of(2022, 3, 14));
		
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
		
		pch2.setEnddate(LocalDate.of(2022, 3, 14));
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			pchService.saveProductcosthistory(pch2);
		});
	}
	
	
	// <------------------------> Edit <------------------------>
	
	@Test
	void testThatEdit() {
		when(pchRepository.findById(pch0.getId())).thenReturn(pch0op);
		when(pchRepository.save(pch0)).thenReturn(pch0);
		Productcosthistory test = pchService.editProductcosthistory(pch1, pch0.getId());
		assertEquals(test.getId(), pch1.getId());
		assertEquals(test.getProduct(), pch1.getProduct());
		assertEquals(test.getEnddate(), pch1.getEnddate());
		assertEquals(test.getStandardcost(), pch1.getStandardcost());
	}
	
	
	// <------------------------> Edit Throws <------------------------>
	
	@Test
	void testExceptionEdit() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			when(pchRepository.findById(pch0.getId())).thenReturn(pch0op);
			pchService.editProductcosthistory(pch3, pch0.getId());
		});
		
		pch3.setEnddate(LocalDate.of(2022, 3, 14));
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			when(pchRepository.findById(pch0.getId())).thenReturn(pch0op);
			pchService.editProductcosthistory(pch3, pch0.getId());
		});
	}
	
	// <------------------------> Empty Edit <------------------------>
	
	@Test
	void testExceptionEditEmpty() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			when(pchRepository.findById(pch0.getId())).thenReturn(pch0op);
			pchService.editProductcosthistory(pch4, pch0.getId());
		});
		
		Product proc = new Product();
		proc.setProductid(1);
		pch2.setProduct(proc);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			when(pchRepository.findById(pch0.getId())).thenReturn(pch0op);
			pchService.editProductcosthistory(pch4, pch0.getId());
		});
		
		pch4.setEnddate(LocalDate.of(2022, 3, 14));
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			when(pchRepository.findById(pch0.getId())).thenReturn(pch0op);
			pchService.editProductcosthistory(pch4, pch0.getId());
		});
	}
	
}
