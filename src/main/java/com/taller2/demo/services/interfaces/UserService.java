package com.taller2.demo.services.interfaces;

import java.util.Optional;

import com.taller2.demo.model.prod.UserApp;

public interface UserService {
	public void save(UserApp user);

	public Optional<UserApp> findById(int id);

	public Iterable<UserApp> findAll();
	
	public void delete(UserApp user);
}
