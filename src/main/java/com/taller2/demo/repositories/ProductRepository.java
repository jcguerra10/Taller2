package com.taller2.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.taller2.demo.model.prod.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

}
