package com.holler.springsec.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.holler.springsec.models.User;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
	
	User findByUsername(String username);
	
	List <User> findAll();
	
	Optional<User> findById(Long id);
	
	void deleteById(Long id);
}
