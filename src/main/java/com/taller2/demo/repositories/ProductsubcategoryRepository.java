package com.taller2.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.taller2.demo.model.prod.Productsubcategory;

@Repository
public interface ProductsubcategoryRepository extends CrudRepository<Productsubcategory, Integer> {

}
