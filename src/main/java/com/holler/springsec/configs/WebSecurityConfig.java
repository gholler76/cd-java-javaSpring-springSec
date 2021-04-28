package com.holler.springsec.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private UserDetailsService userDetailsService;
	
	public WebSecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
    
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
        	// Allows restricting access based upon the HttpServletRequest
            authorizeRequests()
            	// PathMatcher implementation for Ant-style path patterns
            	// we are allowing everything in that matches "/static" and "/register" to be permitted to everyone
            	// "/static" is for our assets and "/register" is for the GET and POST request for registration
                .antMatchers("/static/**", "/register").permitAll()
                // grants access to "/admin" pages to users with ADMIN role
                .antMatchers("/admin/**").access("hasRole('ADMIN')")
                // maps any request and specify URLs that are allowed by authenticated users only
                .anyRequest().authenticated()
                .and()
            // specifies to support form based authentication. Now, our users are going to be authenticated via a FORM
            .formLogin()
            	// specifies the URL to send users to if login is required
                .loginPage("/login")
                .permitAll()
                .and()
            // provides logout support
            // the default is that accessing the URL "/logout" will log the user out by invalidating the HTTP Session 
            // cleans up any rememberMe() authentication that was configured, clears the SecurityContextHolder
            // then redirect to "/login?success".
            .logout()
                .permitAll();
    }
    
	// this method is configuring Spring Security to use our custom implementation of the UserDetailsService with Bcrypt.
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }   
	
}