package com.bloff.pharmacy.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bloff.pharmacy.app.entity.Type;
import com.bloff.pharmacy.app.service.TypeService;

@Controller
@RequestMapping("/types")
public class TypeController {
	
	private TypeService typeService;
	
	@Autowired
	public TypeController(TypeService typeService) {
		this.typeService = typeService;
	}

	@GetMapping("/listTypes")
	public String getList(Model theModel) {
		
		List<Type> types = typeService.findAll();
		
		theModel.addAttribute("types", types);
		
		return "type/types-list";
	}
	
	@GetMapping("/addType")
	public String addType(Model theModel) {
		
		Type theType = new Type();
		
		theModel.addAttribute("type", theType);
		
		return "type/addType-form";
	}
	
	@PostMapping("/saveType")
	public String saveType(@Valid @ModelAttribute("type")Type type, BindingResult binding) {
		
		if(binding.hasErrors())
			return "type/addType-form";
		
		typeService.save(type);
		
		return "redirect:/types/listTypes";
	}
	
	@GetMapping("/updateType/{typeId}")
	public String updateType(@PathVariable("typeId")Long typeId, Model theModel) {
		
		Type theType = typeService.findById(typeId);
		
		theModel.addAttribute("type", theType);
		
		typeService.save(theType);
		
		return "type/addType-form";
	}
	
	@GetMapping("/deleteType/{typeId}")
	public String deleteType(@PathVariable("typeId")Long typeId) {
		
		typeService.deleteById(typeId);
		
		return "redirect:/types/listTypes";
	}
	
}
