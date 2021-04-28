package com.holler.springsec.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.holler.springsec.models.Role;
import com.holler.springsec.models.User;
import com.holler.springsec.repos.RoleRepo;


 
@Service
public class RoleService {
    private RoleRepo roleRepo;
    
    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }
    

    
    public void saveRole(Role Role) {
    	roleRepo.save(Role);
    }
    
    public Role findRoleById(Long id) {
    	Optional<Role> u = roleRepo.findById(id);
    	
    	if(u.isPresent()) {
            return u.get();
    	} else {
    	    return null;
    	}
    }

    
    public List<Role> findAllRoles() {
        return roleRepo.findAll();
    }
       
}

