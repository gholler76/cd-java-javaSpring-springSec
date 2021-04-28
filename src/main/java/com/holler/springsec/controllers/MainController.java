package com.holler.springsec.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.holler.springsec.models.Role;
import com.holler.springsec.models.User;
import com.holler.springsec.services.RoleService;
import com.holler.springsec.services.UserService;
import com.holler.springsec.validators.UserValidator;

@Controller
public class MainController {
    
	private UserService userService;
	private RoleService roleService;
    private UserValidator userValidator;
    
    public MainController(UserService userService, RoleService roleService, UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
    }

//****************************************************
// LOGIN
//****************************************************
    
	// when the credentials are wrong, Spring Security redirects the client to the /login?error url
	// furthermore, when a client logs out via the form in our homePage.jsp file, Spring Security redirects them to /login?logout url
	// therefore, all we do is set a couple of optional request parameters, check for their existence, and add messages accordingly
	// now, we can update our view to display the messages
    @GetMapping("/login")
    public String login(
    		@RequestParam(value="error", required=false) String error,
    		@RequestParam(value="logout", required=false) String logout,
    		Model model) {
    	model.addAttribute("user", new User());
    	if(error != null) {
    		model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
            }
        if(logout != null) {
        	model.addAttribute("logoutMessage", "Logout Successful!");
        	}
        return "login.jsp";
    }
     
     
     @RequestMapping(value = {"/", "/home"})
     public String home(Principal principal, Model model) {
         // our home method accepts GET requests for "/" and "/home" urls
    	 // after a successful authentication, we are able to get the name of our principal (current user) via the .getName() method
         String username = principal.getName();
         model.addAttribute("currentUser", userService.findByUsername(username));
         return "home.jsp";
     }

//+++++++++++++++++++++++++++++++++++++++++++++++++++
// REGISTRATION
//+++++++++++++++++++++++++++++++++++++++++++++++++++
     
     @PostMapping("/register")
     public String registerPost(
    		 @Valid
    		 @ModelAttribute("user") User user,
    		 BindingResult result,
    		 Model model,
    		 HttpSession session) {
    	 System.out.println(user.getPassword());
    	 System.out.println(user.getPasswordConfirmation());
    	 
    	 userValidator.validate(user, result);
    	 if (result.hasErrors()) {
    		 return "login.jsp";
    	 }
    	 // set user type with userService.saveUserWithAdminRole(user) or saveWithUserRole(user) 
    	 // userService.saveWithUserRole(user);
    	 userService.saveWithUserRole(user);
    	 return "redirect:/home";
     }
     
//++++++++++++++++++++++++++++++++++++++++++++++++++++
// ADMIN
//++++++++++++++++++++++++++++++++++++++++++++++++++++

     @RequestMapping("/admin")
     public String adminPage(
    		 Principal principal,
    		 Model model) {
         String username = principal.getName();
         User currentUser = userService.findByUsername(username);
         model.addAttribute("currentUser", userService.findByUsername(username));
         List users = userService.findAllUsers();
         model.addAttribute("users", (List)  userService.findAllUsers());
         System.out.println(users);
         return "admin.jsp";
     }
     
//++++++++++++++++++++++++++++++++++++++++++++++++++++
// USER ADMINISTRATION
//++++++++++++++++++++++++++++++++++++++++++++++++++++
     @DeleteMapping("/delete")
     public String deleteUser(
    		 @RequestParam(value="deleteId") Long id,
    		 Principal principal,
    		 Model model) {
		 String username = principal.getName();
		 User currentUser = userService.findByUsername(username);
		 model.addAttribute("currentUser", userService.findByUsername(username));
		 model.addAttribute("users", (List)  userService.findAllUsers());
    	 
		 User u = userService.findUserById(id);
    	 List<Role> roles = u.getRoles();
    	 roles.removeAll(roles);
    	 u.setRoles(roles);
    	 userService.saveUser(u);
		 userService.deleteUser(id);
    	 
    	 return "admin.jsp";
     }
     
     @PutMapping("/makeadmin")
     public String makeAdmin(
    		 @RequestParam(value="adminId") Long id,
    		 Principal principal,
    		 Model model) {
    	 System.out.println(id);
    	 String username = principal.getName();
    	 User currentUser = userService.findByUsername(username);
    	 model.addAttribute("currentUser", userService.findByUsername(username));
    	 model.addAttribute("users", (List)  userService.findAllUsers());
    	 
    	 User newAdmin = userService.findUserById(id);
    	 System.out.println(newAdmin);
    	 userService.saveUserWithAdminRole(newAdmin);
    	 
    	 return "admin.jsp";
     }
}