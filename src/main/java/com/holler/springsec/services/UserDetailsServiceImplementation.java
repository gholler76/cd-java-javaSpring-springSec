package com.holler.springsec.services;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.holler.springsec.models.Role;
import com.holler.springsec.models.User;
import com.holler.springsec.repos.UserRepo;
@Service
public class UserDetailsServiceImplementation implements UserDetailsService {
    private UserRepo userRepo;
    
    public UserDetailsServiceImplementation(UserRepo userRepo){
        this.userRepo = userRepo;
    }
    
    // Finds the user by their email
    // if a user is found, it returns it with the correct authorities
    // if the email does not exist, the method throws an error
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user));
    }
    
    // returns a list of authorities/permissions for a specific user
    // for example, our clients can be 'user', 'admin', or both
    // for Spring Security to implement authorization, we must get the name of the possibles roles for current user from our db
    // then create a new `SimpleGrantedAuthority' object with those roles
    private List<GrantedAuthority> getAuthorities(User user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for(Role role : user.getRoles()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
            authorities.add(grantedAuthority);
        }
        return authorities;
    }
}

