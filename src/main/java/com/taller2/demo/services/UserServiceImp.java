package com.taller2.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taller2.demo.model.prod.UserApp;
import com.taller2.demo.repositories.UserRepository;
import com.taller2.demo.services.interfaces.UserService;

@Service
public class UserServiceImp implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void save(UserApp user) {
		userRepository.save(user);
	}

	@Override
	public Optional<UserApp> findById(int id) {
		return userRepository.findById(id);
	}

	@Override
	public Iterable<UserApp> findAll() {
		return userRepository.findAll();
	}

	@Override
	public void delete(UserApp user) {
		userRepository.delete(user);
	}

}
