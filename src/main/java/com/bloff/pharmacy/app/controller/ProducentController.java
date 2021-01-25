package com.bloff.pharmacy.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bloff.pharmacy.app.entity.Producent;
import com.bloff.pharmacy.app.service.ProducentService;
import com.bloff.pharmacy.app.service.UserService;

@Controller
@RequestMapping("/producents")
public class ProducentController {// extends AdminController {

	private AdminController adminController;

	private ProducentService producentService;
	
	private UserService userService;
	
	@Autowired
	public ProducentController(AdminController adminController, ProducentService producentService,
			UserService userService) {
		this.adminController = adminController;
		this.producentService = producentService;
		this.userService = userService;
	}

	@GetMapping("/listProducents")
	public String showList(Model theModel) {
//test
System.out.println("producents list/username: " + adminController.loadUsername());
System.out.println("producents list/admin: " + adminController.checkRole());
		
		// get logged in user's id
		Long userId = userService.findByUserName(adminController.loadUsername()).getId();
		
		List<Producent> producents = null;
		
		if(adminController.checkRole()) {
			// find all of producents for admin
			producents = producentService.findAll();
		}
		else {
			// find all of producents for logged in user
			producents = producentService.findAllByUserId(userId);
		}
		// add producent to the model
		theModel.addAttribute("producents", producents);
		
		return "producent/producents-list";
	}
	
	@GetMapping("/addProducent")
	public String addProducent(Model theModel) {
		
		// create new object of Producent
		Producent theProducent = new Producent();
		
		// add new object to the model
		theModel.addAttribute("producent", theProducent);
		
		return "producent/addProducent-form";
	}
	
	@PostMapping("/saveProducent")
	public String saveProducent(@Valid@ModelAttribute("producent")Producent theProducent, BindingResult binding) {

		if(binding.hasErrors())
			return "producent/addProducent-form";
		
//test
System.out.println("producents list/username: " + adminController.loadUsername());
		
		
		theProducent.setActiv(1);
		theProducent.setUserId(userService.findByUserName(adminController.loadUsername()).getId());
		
		producentService.save(theProducent);
		
		return "redirect:/producents/listProducents";
	}
	
	@GetMapping("/updateProducent")
	public String updateProducent(@RequestParam("producentId")Long producentId, Model theModel) {
//test
System.out.println("producents list/username: " + adminController.loadUsername());
		
		
		
		Producent theProducent = producentService.findById(producentId);
		
		String page= null;
		
		// protection against unauthorized access to data
		if(userService.findByUserName(adminController.loadUsername()).equals(
				userService.findByUserId(theProducent.getUserId())) || adminController.checkRole()) {
			theModel.addAttribute("producent", theProducent);
			page = "producent/addProducent-form";
		}
		else {
			System.out.println("niezgadza sie id user dla producenta");
			
			page = "error";
		}
			
		return page;
	}
	
	@GetMapping("/deleteProducent")
	public String delete(@RequestParam("producentId") Long producentId) {
		
		producentService.deleteById(producentId);

		return "redirect:/producents/listProducents";
	}
}
