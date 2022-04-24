package com.taller2.demo.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taller2.demo.model.prod.Productsubcategory;
import com.taller2.demo.repositories.ProductsubcategoryRepository;
import com.taller2.demo.services.interfaces.ProductsubcategoryService;

@Service
public class ProductsubcategoryImp implements ProductsubcategoryService {

private ProductsubcategoryRepository psRepository;
	
	public ProductsubcategoryImp(ProductsubcategoryRepository psRepository) {
		this.psRepository = psRepository;
	}

	@Transactional
	@Override
	public Productsubcategory saveProductsubcategory(Productsubcategory pc) {
		if(pc == null)
			throw new NullPointerException("Null Object");
		return psRepository.save(pc);
	}

	@Transactional
	@Override
	public Productsubcategory editProductsubcategory(Productsubcategory pc, Integer i) {
		Optional<Productsubcategory> op = psRepository.findById(i);
		Productsubcategory opLoc = op.get();
		if(pc == null)
			throw new NullPointerException("Null Object");
		opLoc.setName(pc.getName());
		return psRepository.save(opLoc);
	}
}
