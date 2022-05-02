package com.taller2.demo.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.taller2.demo.model.prod.Location;
import com.taller2.demo.model.prod.Productinventory;
import com.taller2.demo.model.prod.ProductinventoryPK;

@Repository
public interface ProductInventoryRepository extends CrudRepository<Productinventory, Integer> {

	Optional<Productinventory> findById(ProductinventoryPK pk);
	
}
