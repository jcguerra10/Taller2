package com.taller2.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.taller2.demo.model.prod.UserApp;

@Repository
public interface UserRepository extends CrudRepository<UserApp, Integer> {

	UserApp findByUsername(String username);

}