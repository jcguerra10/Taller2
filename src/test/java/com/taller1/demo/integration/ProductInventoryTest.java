package com.taller1.demo.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.taller2.demo.model.prod.Location;
import com.taller2.demo.model.prod.Product;
import com.taller2.demo.model.prod.Productinventory;
import com.taller2.demo.repositories.LocationRepository;
import com.taller2.demo.repositories.ProductInventoryRepository;
import com.taller2.demo.services.ProductInventoryServiceImp;

class ProductInventoryTest {

	@Mock
	private ProductInventoryRepository piRepository;
	
	@Mock
	private LocationRepository locRepository;

	@InjectMocks
	private ProductInventoryServiceImp piService;

	private Productinventory pInventory0;
	private Optional<Productinventory> pInventory0op;
	
	private Productinventory pInventory1;
	private Productinventory pInventory2;
	private Productinventory pInventory3;
	private Productinventory pInventory4;
	

	// <------------------------> Setups <------------------------>
	
	@BeforeEach
	void setUp1() {
		MockitoAnnotations.openMocks(this);
		
		Product proc = new Product();
		proc.setProductid(1);
		Location loc = new Location();
		pInventory0 = new Productinventory();
		pInventory0.setProduct(proc);
		pInventory0.setLocation(loc);
		pInventory0.setQuantity(2);
	}
	
	@BeforeEach
	void setUp2() {
		MockitoAnnotations.openMocks(this);
		
		Product proc = new Product();
		proc.setProductid(1);
		Location loc = new Location();
		pInventory2 = new Productinventory();
		pInventory2.setProduct(proc);
		pInventory2.setLocation(loc);
		pInventory2.setQuantity(-1);
	}
	
	@BeforeEach
	void setUp3() {
		MockitoAnnotations.openMocks(this);
		
		pInventory4 = new Productinventory();
	}
	
	@BeforeEach
	void setUp4() {
		MockitoAnnotations.openMocks(this);
		
		pInventory0op = Optional.of(pInventory0);
		
		Product proc1 = new Product();
		proc1.setProductid(1);
		Location loc1 = new Location();
		pInventory1 = new Productinventory();
		pInventory1.setProduct(proc1);
		pInventory1.setLocation(loc1);
		pInventory1.setQuantity(7);
	}
	
	@BeforeEach
	void setUp5() {
		MockitoAnnotations.openMocks(this);
		
		Product proc = new Product();
		proc.setProductid(1);
		Location loc = new Location();
		pInventory3 = new Productinventory();
		pInventory3.setProduct(proc);
		pInventory3.setLocation(loc);
		pInventory3.setQuantity(-5);
	}
	
	// <------------------------> Save <------------------------>
	
	@Test
	void testThatSave() {
		when(piRepository.save(pInventory0)).thenReturn(pInventory0);
		assertNotNull(piService.saveProductInventory(pInventory0));
	}
	
	@Test
	void testConstraints() {
		when(piRepository.save(pInventory0)).thenReturn(pInventory0);
		Productinventory test = piService.saveProductInventory(pInventory0);
		assertNotNull(test.getProduct());
		assertNotNull(test.getLocation());
		assertTrue(test.getQuantity() > 0);
	}

	// <------------------------> Save Throws <------------------------>
	
	@Test
	void testExceptionSave() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			piService.saveProductInventory(pInventory2);
		});
		
	}

	// <------------------------> Empty Save <------------------------>
	
	@Test
	void testExceptionSaveEmpty() {
		Assertions.assertThrows(NullPointerException.class, () -> {
			piService.saveProductInventory(pInventory4);
		});
		
		Product proc = new Product();
		proc.setProductid(1);
		pInventory4.setProduct(proc);
		
		Assertions.assertThrows(NullPointerException.class, () -> {
			piService.saveProductInventory(pInventory4);
		});
		
		Location loc = new Location();
		loc.setLocationid(1);
		pInventory4.setLocation(loc);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			piService.saveProductInventory(pInventory4);
		});
	}

	// <------------------------> Edit <------------------------>
	
	@Test
	void testThatEdit() {
		when(piRepository.findById(1)).thenReturn(pInventory0op);
		when(piRepository.save(pInventory0)).thenReturn(pInventory0);
		Productinventory test = piService.editProductInventory(pInventory1, 1);
		assertEquals(test.getId(), pInventory1.getId());
		assertEquals(test.getLocation(), pInventory1.getLocation());
		assertEquals(test.getProduct(), pInventory1.getProduct());
		assertEquals(test.getQuantity(), pInventory1.getQuantity());
		assertNotNull(piService.editProductInventory(pInventory1, 1));
	}

	// <------------------------> Edit Throws <------------------------>
	
	@Test
	void testExceptionEdit() {
		when(piRepository.findById(1)).thenReturn(pInventory0op);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			piService.editProductInventory(pInventory3, 1);
		});
	}


	// <------------------------> Empty Edit <------------------------>
	
	@Test
	void testExceptionEditEmpty() {
		when(piRepository.findById(1)).thenReturn(pInventory0op);
		
		Assertions.assertThrows(NullPointerException.class, () -> {
			piService.editProductInventory(pInventory4, 1);
		});
		
		Product proc = new Product();
		proc.setProductid(1);
		pInventory4.setProduct(proc);
		
		Assertions.assertThrows(NullPointerException.class, () -> {
			piService.editProductInventory(pInventory4, 1);
		});
		
		Location loc = new Location();
		loc.setLocationid(1);
		pInventory4.setLocation(loc);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			piService.editProductInventory(pInventory4, 1);
		});
	}	
	
}
