package com.taller2.demo.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taller2.demo.model.prod.Productinventory;
import com.taller2.demo.repositories.LocationRepository;
import com.taller2.demo.repositories.ProductInventoryRepository;
import com.taller2.demo.services.interfaces.ProductInventoryService;

@Service
public class ProductInventoryServiceImp implements ProductInventoryService {

	private ProductInventoryRepository proinRepository;
	private LocationRepository lr;
	
	public ProductInventoryServiceImp(ProductInventoryRepository proinRepository, LocationRepository lr) {
		this.proinRepository = proinRepository;
		this.lr = lr;
	}

	@Transactional
	@Override
	public Productinventory saveProductInventory(Productinventory proInventory) {
		if (proInventory == null)
			throw new NullPointerException();
		if (proInventory.getLocation() == null) 
			throw new NullPointerException();
		if (lr.existsById(proInventory.getLocation().getLocationid()))
			throw new NullPointerException();
		if (proInventory.getQuantity() == null)
			throw new IllegalArgumentException("Quantity Null");
		if (proInventory.getQuantity() < 0)
			throw new IllegalArgumentException("Quantity is not Greater than 0");
		return proinRepository.save(proInventory);
	}

	@Transactional
	@Override
	public Productinventory editProductInventory(Productinventory proInventory, Integer id) {
		Optional<Productinventory> op = proinRepository.findById(id);
		Productinventory opLoc = op.get();
		if (proInventory == null)
			throw new NullPointerException();
		if (proInventory.getLocation() == null) 
			throw new NullPointerException();
		if (lr.existsById(proInventory.getLocation().getLocationid()))
			throw new NullPointerException();
		if (proInventory.getQuantity() == null)
			throw new IllegalArgumentException("Quantity Null");
		if (proInventory.getQuantity() < 0)
			throw new IllegalArgumentException("Quantity is not Greater than 0");
		opLoc.setId(proInventory.getId());
		opLoc.setProduct(proInventory.getProduct());
		opLoc.setLocation(proInventory.getLocation());
		opLoc.setQuantity(proInventory.getQuantity());
		return proinRepository.save(opLoc);
	}

}