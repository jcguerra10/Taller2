package com.taller2.demo.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taller2.demo.model.prod.Location;
import com.taller2.demo.repositories.LocationRepository;
import com.taller2.demo.services.interfaces.LocationService;

@Service
public class LocationServiceImp implements LocationService {

	private LocationRepository locationRepository;
	
	public LocationServiceImp(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}
	
	@Transactional
	@Override
	public Location saveLocation(Location loc) {
		if (loc == null )
			throw new NullPointerException("Parameter Null");
		if (loc.getName() == null)
			throw new IllegalArgumentException("Name Null");
		if (loc.getName().length() < 5)
			throw new IllegalArgumentException("Name Length");
		if (loc.getAvailability() == null)
			throw new IllegalArgumentException("Availabilty Null");
		if (loc.getAvailability().intValue() < 1 || loc.getAvailability().intValue() > 10)
			throw new IllegalArgumentException("Availability");
		if (loc.getCostrate() == null)
			throw new IllegalArgumentException("CostRate Null");
		if (loc.getCostrate().intValue() < 0 || loc.getCostrate().intValue() > 1)
			throw new IllegalArgumentException();
		return locationRepository.save(loc);
	}

	@Transactional
	@Override
	public Location editLocation(Location loc, Integer locId) {
		Optional<Location> op = locationRepository.findById(locId);
		Location opLoc = op.get();
		if (loc == null )
			throw new NullPointerException("Parameter Null");
		if (loc.getName() == null)
			throw new IllegalArgumentException("Name Null");
		if (loc.getName().length() < 5)
			throw new IllegalArgumentException("Name Length");
		if (loc.getAvailability() == null)
			throw new IllegalArgumentException("Availabilty Null");
		if (loc.getAvailability().intValue() < 1 || loc.getAvailability().intValue() > 10)
			throw new IllegalArgumentException("Availability");
		if (loc.getCostrate() == null)
			throw new IllegalArgumentException("CostRate Null");
		if (loc.getCostrate().intValue() < 0 || loc.getCostrate().intValue() > 1)
			throw new IllegalArgumentException();
		opLoc.setName(loc.getName());
		opLoc.setAvailability(loc.getAvailability());
		opLoc.setCostrate(loc.getCostrate());
		return locationRepository.save(opLoc);
	}

}
