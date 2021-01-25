package com.bloff.pharmacy.app.service;

import java.util.List;

import com.bloff.pharmacy.app.entity.Medicine;

public interface MedicineService {

	public List<Medicine> findAllByUsername(String username);
	
	public List<Medicine> findAllByUserId(Long id);
	
	public List<Medicine> findAll();
	
	public Medicine findById(Long medicineId);
	
	public Medicine save(Medicine theMedicine);
	
	public void deleteById(Long medicineId);

	public List<Medicine> findByMedicineName(String searchName, String username);

	public void deleteByUserId(Long userId);
	
}
