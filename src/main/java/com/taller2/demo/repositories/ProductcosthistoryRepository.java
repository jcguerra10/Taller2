package com.taller2.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.taller2.demo.model.prod.Productcosthistory;
import com.taller2.demo.model.prod.ProductcosthistoryPK;

@Repository
public interface ProductcosthistoryRepository extends CrudRepository<Productcosthistory, Integer> {

}
