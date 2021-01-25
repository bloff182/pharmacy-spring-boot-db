package com.bloff.pharmacy.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bloff.pharmacy.app.dao.MedicineRepository;
import com.bloff.pharmacy.app.entity.Medicine;

@Service
public class MedicineServiceImpl implements MedicineService {

	@Autowired
	private MedicineRepository medicineDao;

	@Override
	public List<Medicine> findAllByUsername(String username) {

		return medicineDao.findAllByUser_Username(username);
	}

	@Override
	public Medicine findById(Long medicineId) {
		
		Optional<Medicine> result = medicineDao.findById(medicineId);
		
		Medicine medicine = null;
		
		if (result.isPresent()) {
			medicine = result.get();
		} else {
			throw new RuntimeException("Didn't find medicine by id = " + medicineId);
		}
		
		return medicine;
	}

	@Override
	public Medicine save(Medicine theMedicine) {

		return medicineDao.save(theMedicine);
	}

	@Override
	public void deleteById(Long medicineId) {

		medicineDao.deleteById(medicineId);
	}

	@Override
	public List<Medicine> findAll() {
		return medicineDao.findAll();
	}

	@Override
	public List<Medicine> findByMedicineName(String searchName, String username) {
		
		List<Medicine> results = null;
		
		if (searchName != null && (searchName.trim().length() > 0)) {
			results = medicineDao.findByMedicineNameContainsAndUser_UsernameIgnoreCase(searchName, username);
		} else {
			
			results = findAllByUsername(username);
		}

		return results;
	}

	@Override
	public List<Medicine> findAllByUserId(Long id) {
		
		return medicineDao.findAllByUserId(id);
	}

	@Override
	public void deleteByUserId(Long userId) {

		medicineDao.deleteByUserId(userId);
		
	}

}
