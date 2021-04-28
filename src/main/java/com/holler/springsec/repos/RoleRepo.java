package com.holler.springsec.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.holler.springsec.models.Role;



@Repository
public interface RoleRepo extends CrudRepository<Role, Long> {
	
	List<Role> findAll();
	    
	Optional<Role> findById(Long id);
    
	List<Role> findByName(String name);

}