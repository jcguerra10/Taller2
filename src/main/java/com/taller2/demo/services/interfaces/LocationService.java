package com.taller2.demo.services.interfaces;

import com.taller2.demo.model.prod.Location;

public interface LocationService {
	public Location saveLocation(Location loc);
	public Location editLocation(Location loc, Integer locId);
}
