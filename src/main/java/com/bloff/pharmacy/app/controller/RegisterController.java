package com.bloff.pharmacy.app.controller;

import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bloff.pharmacy.app.crm.CrmUser;
import com.bloff.pharmacy.app.entity.User;
import com.bloff.pharmacy.app.service.UserService;

@Controller
@RequestMapping("/register")
public class RegisterController {
	
    @Autowired
    private UserService userService;
	
    private Logger logger = Logger.getLogger(getClass().getName());
    
    // kod ten wykona sie pierwszy w kontrolerze dla kazdego żądania internetowego
    
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
	
		// usuwamy biale przestrzenie tj spacje
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}	
	
	@GetMapping("/showRegistrationForm")
	public String showMyLoginPage(Model theModel) {
		
		theModel.addAttribute("crmUser", new CrmUser());
		
		return "security/registration-form";
	}

	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(
				@Valid @ModelAttribute("crmUser") CrmUser theCrmUser, 
				BindingResult theBindingResult, 
				Model theModel) {
		
		String userName = theCrmUser.getUserName();
		logger.info("Processing registration form for: " + userName);
		
		// walidacja
		 if (theBindingResult.hasErrors()){
			 return "security/registration-form";
	        }

		// sprawdzamy czy uzytkownik istnieje w db
        User existing = userService.findByUserName(userName);
        if (existing != null){
        	theModel.addAttribute("crmUser", new CrmUser());
			theModel.addAttribute("registrationError", "User name already exists.");

			logger.warning("User name already exists.");
        	return "security/registration-form";
        }
        
        // tworzymy konto uzytkownika
        userService.save(theCrmUser);
        
        logger.info("Successfully created user: " + userName);
        
        return "security/registration-confirmation";		
	}
}
