package com.bloff.pharmacy.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bloff.pharmacy.app.entity.Medicine;
import com.bloff.pharmacy.app.entity.User;
import com.bloff.pharmacy.app.service.MedicineService;
import com.bloff.pharmacy.app.service.UserService;

@Controller
@RequestMapping("/api")
public class AdminController {

	private MedicineService medicineService;
	
	private UserService userService;
	
	private String username;
	
	private Boolean admin;
	
	@Autowired
	public AdminController(MedicineService medicineService, UserService userService) {
		this.medicineService = medicineService;
		this.userService = userService;
	}

	public AdminController() {
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	// load logged in username
	public String loadUsername() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		username = auth.getName();
		
		return username;
	}
	
	// check if the logged in user is admin
	public Boolean checkRole() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		admin = false;
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
			admin = true;
		}
		return admin;
	}
	
	@GetMapping("/welcome")
	public String showMain(Model theModel) {
		
		loadUsername();
		
		theModel.addAttribute("userFirstName", userService.findByUserName(username).getFirstName());
		theModel.addAttribute("userLastName", userService.findByUserName(username).getLastName());
		theModel.addAttribute("userEmail", userService.findByUserName(username).getEmail());
		theModel.addAttribute("userIdW", userService.findByUserName(username).getId());

		return "welcome";
	}
	
	@GetMapping("/listMedicine")
	public String showList(Model theModel) {
		
		loadUsername();
		
		// get the list of users
		List<User> users = userService.findAllUsers();
		
		// add the list of users to the model to expose data in dropdown list
		theModel.addAttribute("users", users);
		
		// get the list of medicines
		List<Medicine> medicines = medicineService.findAll();
		
		// add the list of medicines to the model
		theModel.addAttribute("medicines", medicines);
		
		return "medicine/listMedicineForAdmin";
	}
	
	@GetMapping("/search")
	public String searchMedicin(@RequestParam("theSearchUsername")String theSearchUsername, Model theModel) {
		// get the user list
		List<User> users = userService.findAllUsers();
		
		// add the list of users to the model to expose data in dropdown list
		theModel.addAttribute("users", users);
		
		// search the user or list of users
		List<Medicine> medicines = medicineService.findAllByUsername(theSearchUsername);
		
		theModel.addAttribute("medicines", medicines);
		
		return "medicine/listMedicineForAdmin";
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((admin == null) ? 0 : admin.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdminController other = (AdminController) obj;
		if (admin == null) {
			if (other.admin != null)
				return false;
		} else if (!admin.equals(other.admin))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	
	
}
