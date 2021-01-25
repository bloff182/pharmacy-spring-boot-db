package com.bloff.pharmacy.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bloff.pharmacy.app.entity.Destiny;
import com.bloff.pharmacy.app.entity.Medicine;
import com.bloff.pharmacy.app.entity.Producent;
import com.bloff.pharmacy.app.entity.Type;
import com.bloff.pharmacy.app.entity.User;
import com.bloff.pharmacy.app.service.DestinyService;
import com.bloff.pharmacy.app.service.MedicineService;
import com.bloff.pharmacy.app.service.ProducentService;
import com.bloff.pharmacy.app.service.TypeService;
import com.bloff.pharmacy.app.service.UserService;

@Controller
@RequestMapping("/pharmacy")
public class MedicineController {

	@Autowired
	private MedicineService medicineService;
	
	@Autowired
	private ProducentService producentService;
	
	@Autowired
	private TypeService typeService;
	
	@Autowired
	private DestinyService destinyService;
	
	@Autowired
	private UserService userService;
	
	private String username;

	private Long userId;
	
	public Long loadUserId() {
	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// get a name of logged user
		username = auth.getName();
		
		// get a id of logged user
		userId = userService.findByUserName(username).getId();
		
		return userId;
	}
	
	
	@GetMapping("/listMedicine")
	public String showList(Model theModel) {

		loadUserId();
//test
System.out.println("medicine/list/user: " + username);		
		// get a list of medicines
		List<Medicine> medicines = medicineService.findAllByUsername(username);
		
		System.out.println("Medicine List: userId = " + userId);
		
		
		// add list to the model
		theModel.addAttribute("medicines", medicines);
		
		return "medicine/listMedicine";
	}
	
	@GetMapping("/addMedicine")
	public String addMedicine(Model theModel) {
		
		// load data to dropdown list
		loadData(theModel);
//test
System.out.println("medicine add/username: " + username);
		
		Medicine theMedicine = new Medicine();
		
		theModel.addAttribute("medicine", theMedicine);
		
		return "medicine/addMedicine-form";
	}
	
	@PostMapping("/saveMedicine")
	public String saveMedicine(@Valid@ModelAttribute("medicine")Medicine theMedicine, BindingResult binding) {
		
		if(binding.hasErrors())
			return "medicine/addMedicine-form";
//test
System.out.println("medicine save/username: " + username);		
	
		// get logged user to set it for saved medicine
		User theUser = userService.findByUserName(username);
		
		// set user id for saved medicine
		theMedicine.setUser(theUser);
		
		// save medicine
		medicineService.save(theMedicine);
		
		return "redirect:/pharmacy/listMedicine";
	}
	
	@GetMapping("/updateMedicine")
	public String updateMedicine(@RequestParam("medicineId")Long medicineId, Model theModel) {

		// load data to dropdown list
		loadData(theModel);
		
		// get the medicine to update
		Medicine theMedicine = medicineService.findById(medicineId);
		
		// set medicine as a model attribute to pre-populate the form
		theModel.addAttribute("medicine", theMedicine);
		
		return "medicine/addMedicine-form";
	}
	
	@GetMapping("/deleteMedicine")
	public String deleteMedicine(@RequestParam("medicineId")Long medicineId) {
		
		medicineService.deleteById(medicineId);
		
		return "redirect:/pharmacy/listMedicine";
	}
	
	
	@GetMapping("/searchMedicine")
	public String searchMedicine(@RequestParam("theSearchName") String searchName, Model theModel) {
//test
System.out.println("medicine search/username: " + username);			
		// pobieram szukany lek
		List<Medicine> medicine = medicineService.findByMedicineName(searchName, username);
		
		// dodaje wyszukany lek do modelu
		theModel.addAttribute("medicines", medicine);
		
		return "medicine/listMedicine";
	}
	
	// metoda do doadawania elementów do dropdown list dla danego usera
	private void loadData(Model theModel) {

		// pobieramy liste producent, types, destiny aby dodac do dropdown list
		List<Producent> producents = producentService.findAllByUserId(userId);
		List<Type> types = typeService.findAll();
		List<Destiny> destinies = destinyService.findAll();
		
		// dodajemy liste producent, types, destiny do modelu aby dodac do dropdown list
		theModel.addAttribute("producents", producents);
		theModel.addAttribute("types", types);
		theModel.addAttribute("destinies", destinies);
	}
	
	
}
