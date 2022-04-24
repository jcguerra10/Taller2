package com.taller1.demo.mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.taller2.demo.model.prod.Location;
import com.taller2.demo.repositories.LocationRepository;
import com.taller2.demo.services.LocationServiceImp;

class LocationTest {

	@Mock
	private LocationRepository locationRepository;

	@InjectMocks
	private LocationServiceImp ls;

	private Location location0;
	private Optional<Location> location0op;

	private Location location2;

	private Location location1;

	private Location location3;

	private Location location4;

	// bestCases

	@BeforeEach
	void setUp1() {
		MockitoAnnotations.openMocks(this);

		location0 = new Location();
		location0.setLocationid(1);
		location0.setName("locat");
		location0.setAvailability(new BigDecimal(2));
		location0.setCostrate(new BigDecimal(1));
	}

	@BeforeEach
	void setUp2() {
		MockitoAnnotations.openMocks(this);

		location2 = new Location();
		location2.setLocationid(1);
		location2.setName("lo");
		location2.setAvailability(new BigDecimal(11));
		location2.setCostrate(new BigDecimal(12));
	}

	@BeforeEach
	void setUp3() {
		location4 = new Location();
		location4.setLocationid(1);
	}

	@BeforeEach
	void setUp4() {
		MockitoAnnotations.openMocks(this);

		location0op = Optional.of(location0);

		location1 = new Location();
		location1.setLocationid(1);
		location1.setName("admin");
		location1.setAvailability(new BigDecimal(3));
		location1.setCostrate(new BigDecimal(1));
	}

	@BeforeEach
	void setUp5() {
		MockitoAnnotations.openMocks(this);

		location0op = Optional.of(location0);

		location3 = new Location();
		location3.setLocationid(1);
		location3.setName("lo");
		location3.setAvailability(new BigDecimal(-2));
		location3.setCostrate(new BigDecimal(-1));
	}

	// <------------------------> Save <------------------------>

	@Test
	void testThatSave() {
		when(locationRepository.save(location0)).thenReturn(location0);
		assertNotNull(ls.saveLocation(location0));
	}

	@Test
	void testConstraints() {
		when(locationRepository.save(location0)).thenReturn(location0);
		Location testLoc = ls.saveLocation(location0);
		assertTrue(testLoc.getName().length() >= 5, "ConstraintName");
		assertTrue(testLoc.getAvailability().intValue() >= 1 && testLoc.getAvailability().intValue() <= 10,
				"ConstraintAvailability");
		assertTrue(testLoc.getCostrate().intValue() == 0 || testLoc.getCostrate().intValue() == 1,
				"ConstraintCostRate");
	}

	// <------------------------> Save Throws <------------------------>

	@Test
	void testExceptionName() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ls.saveLocation(location2);
		});
		
		location2.setName("locat");
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ls.saveLocation(location2);
		});
		
		location2.setAvailability(new BigDecimal(1));
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ls.saveLocation(location2);
		});
	}

	// <------------------------> Empty Save <------------------------>

	@Test
	void testExceptionNameEmpty() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ls.saveLocation(location4);
		});

		location4.setName("locat");

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ls.saveLocation(location4);
		});
		
		location4.setAvailability(new BigDecimal(2));
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ls.saveLocation(location4);
		});
	}

	// <------------------------> Edit <------------------------>

	@Test
	void testThatEdit() {
		when(locationRepository.findById(1)).thenReturn(location0op);
		when(locationRepository.save(location0)).thenReturn(location0);
		Location test = ls.editLocation(location1, 1);
		assertEquals(test.getLocationid(), location1.getLocationid());
		assertEquals(test.getName(), location1.getName());
		assertEquals(test.getAvailability(), location1.getAvailability());
		assertEquals(test.getCostrate(), location1.getCostrate());
	}

	// <------------------------> Edit Throws <------------------------>

	@Test
	void testExceptionEdit() {
		when(locationRepository.findById(1)).thenReturn(location0op);

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ls.editLocation(location3, 1);
		});

		location2.setName("locat");

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ls.editLocation(location3, 1);
		});

		location2.setAvailability(new BigDecimal(1));

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ls.editLocation(location3, 1);
		});
	}

	// <------------------------> Edit Empty <------------------------>

	@Test
	void testEmptyEdit() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			when(locationRepository.findById(1)).thenReturn(location0op);
			ls.editLocation(location4, 1);
		});

		location4.setName("locat");

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			when(locationRepository.findById(1)).thenReturn(location0op);
			ls.editLocation(location4, 1);
		});

		location4.setAvailability(new BigDecimal(1));

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			when(locationRepository.findById(1)).thenReturn(location0op);
			ls.editLocation(location4, 1);

		});
	}
}
