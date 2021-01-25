package com.bloff.pharmacy.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

	
	@GetMapping("/showMyLoginPage")
	public String showMyLoginPage() {
		
		return "security/fancy-login-register";
	}

	//  wyswietlenie strony jsp z komunikatem o bledzie dostepu
	@GetMapping("/access-denied")
	public String showAccessDenied() {
		
		return "security/access-denied";
	}

}
