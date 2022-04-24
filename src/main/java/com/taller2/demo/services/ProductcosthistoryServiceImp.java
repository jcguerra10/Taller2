package com.taller2.demo.services;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taller2.demo.model.prod.Productcosthistory;
import com.taller2.demo.model.prod.ProductcosthistoryPK;
import com.taller2.demo.repositories.ProductcosthistoryRepository;
import com.taller2.demo.services.interfaces.ProductcosthistoryService;

@Service
public class ProductcosthistoryServiceImp implements ProductcosthistoryService {

	private ProductcosthistoryRepository pchRepository;
	
	public ProductcosthistoryServiceImp(ProductcosthistoryRepository pchRepository) {
		this.pchRepository = pchRepository;
	}

	@Transactional
	@Override
	public Productcosthistory saveProductcosthistory(Productcosthistory pch) {
		if (pch == null) 
			throw new NullPointerException();
		if (pch.getProduct() == null)
			throw new IllegalArgumentException("Product Not Exist");
		if (pch.getEnddate() == null)
			throw new IllegalArgumentException("EndDate Null");
		if (pch.getEnddate().compareTo(new Timestamp(System.currentTimeMillis())) > 0)
			throw new IllegalArgumentException("End Date Greater Than Actual");
		if (pch.getStandardcost() == null)
			throw new IllegalArgumentException("Standar Cost Null");
		if (pch.getStandardcost().intValue() < 0)
			throw new IllegalArgumentException("Standar Cost");
		return pchRepository.save(pch);
	}

	@Transactional
	@Override
	public Productcosthistory editProductcosthistory(Productcosthistory pch, ProductcosthistoryPK productcosthistoryPK) {
		Optional<Productcosthistory> op = pchRepository.findById(productcosthistoryPK);
		Productcosthistory oppch = op.get();
		if (pch == null) 
			throw new NullPointerException();
		if (pch.getProduct() == null)
			throw new IllegalArgumentException("Product Not Exist");
		if (pch.getEnddate() == null)
			throw new IllegalArgumentException("EndDate Null");
		if (pch.getEnddate().compareTo(new Timestamp(System.currentTimeMillis())) > 0)
			throw new IllegalArgumentException("End Date Greater Than Actual");
		if (pch.getStandardcost() == null)
			throw new IllegalArgumentException("Standar Cost Null");
		if (pch.getStandardcost().intValue() < 0)
			throw new IllegalArgumentException("Standar Cost");
		oppch.setProduct(pch.getProduct());;
		oppch.setEnddate(pch.getEnddate());;
		oppch.setStandardcost(pch.getStandardcost());;
		return pchRepository.save(oppch);
	}

}
