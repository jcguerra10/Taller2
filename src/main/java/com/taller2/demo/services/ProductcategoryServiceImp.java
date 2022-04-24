package com.taller2.demo.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taller2.demo.model.prod.Productcategory;
import com.taller2.demo.repositories.ProductcategoryRepository;
import com.taller2.demo.services.interfaces.ProductcategoryService;

@Service
public class ProductcategoryServiceImp implements ProductcategoryService {
	

	private ProductcategoryRepository pcRepository;
	
	public ProductcategoryServiceImp(ProductcategoryRepository pcRepository) {
		this.pcRepository = pcRepository;
	}

	@Transactional
	@Override
	public Productcategory saveProductcategory(Productcategory pc) {
		if(pc == null)
			throw new NullPointerException("Null Object");
		return pcRepository.save(pc);
	}

	@Transactional
	@Override
	public Productcategory editProductcategory(Productcategory pc, Integer i) {
		Optional<Productcategory> op = pcRepository.findById(i);
		Productcategory opLoc = op.get();
		if(pc == null)
			throw new NullPointerException("Null Object");
		opLoc.setName(pc.getName());
		return pcRepository.save(opLoc);
	}

}
