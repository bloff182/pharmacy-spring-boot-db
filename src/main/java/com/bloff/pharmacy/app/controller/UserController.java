package com.bloff.pharmacy.app.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bloff.pharmacy.app.entity.Producent;
import com.bloff.pharmacy.app.entity.Role;
import com.bloff.pharmacy.app.entity.User;
import com.bloff.pharmacy.app.service.ProducentService;
import com.bloff.pharmacy.app.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

	private AdminController adminController;
	
	private UserService userService;

	private ProducentService producentService;
	
	Collection<Role> roles;
	
	@Autowired
	public UserController(AdminController adminController, UserService userService, ProducentService producentService) {
		this.adminController = adminController;
		this.userService = userService;
		this.producentService = producentService;
	}

	// get a list of users
	@GetMapping("/listUsers")
	public String listUsers(Model theModel) {
		
		// get a list of users
		List<User> users = userService.findAllUsers();
		
		// add list to the model
		theModel.addAttribute("users", users);
		
		return "user/users-list";
	}
	
	@GetMapping("/showSingleUserByUsername")
	public String showSingleUserByUsername(Model theModel) {
		
		// load username
		adminController.loadUsername();
		
		// get User details by username to update
		User theUser = userService.findByUserName(adminController.loadUsername());
		
		// add user to model
		theModel.addAttribute("user5", theUser);
		
		return "user/showData-form";
	}
	
	@PostMapping("/saveForUpdateUser")
	public String saveForUpdateUser(@ModelAttribute("user")User theUser) {
	
		// set roles for updated user - do this because during update we lost all roles for updated user
		theUser.setRoles(roles);
		
		// save updated user
		userService.saveForUpdateUser(theUser);
		
		// check if the logged in user is admin to redirect the appropriate html form
		if(adminController.checkRole())
			return "redirect:/users/listUsers";
		
		return "redirect:/api/welcome";
	}
	
	
	@GetMapping("/updateUser")
	public String updateUser(@RequestParam("userId")Long userId, Model theModel) {

		// get logged in user's username
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String username = auth.getName();
		
		// get updated user
		User theUser = userService.findByUserId(userId);
		
		// get list of roles for updated user - do this because during update we lost all roles for updated user - rebuilt relations
		roles = theUser.getRoles();
		
		String page = null;

		// protection against unauthorized access to data
		if (username.equals(theUser.getUsername()) || adminController.checkRole()) {
			// add user to model
			theModel.addAttribute("user", theUser);
			page = "user/showData-form";
		}
		else
			page = "error";
		
		return page;
	}
	
	@GetMapping("/deleteUser")
	public String deleteUser(@RequestParam("userId")Long userId, Model theModel) {
			
		// deactivate the producer assigned to the user
		setInactiveProducent(userId);
		
		// first - delete roles
		userService.findByUserId(userId).setRoles(null);
		
		// delete the user
		userService.deleteById(userId);
		
		return "redirect:/users/listUsers";
	}
	
	@GetMapping("/searchUser")
	public String searchUser(@RequestParam("searchUserName")String searchUserName, Model theModel) {
		
		List<User> user = userService.searchByUserName(searchUserName);
		
		theModel.addAttribute("user", user);
		
		return "user/users-list"; 
	}
	
	public List<Producent> setInactiveProducent(Long userId){
		
		// get a list of producents assigned to user
		List<Producent> producents = producentService.findAllByUserId(userId);
		
		// set value of zero for activ field
		for(Producent pr : producents)
		{
			pr.setActiv(0);
		}
		return producents;
	}
	
}
