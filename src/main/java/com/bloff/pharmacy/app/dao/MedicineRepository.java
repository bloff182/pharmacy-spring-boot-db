package com.bloff.pharmacy.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bloff.pharmacy.app.entity.Medicine;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

	List<Medicine> findAllByUser_Username(String username);
	
	//@Query("select u from Medicine u where u.medicineName like %:name%")
	//public List<Medicine> findByMedicineNameContainsIgnoreCase(@Param("name")String medicineName);
	List<Medicine> findByMedicineNameContainsAndUser_UsernameIgnoreCase(String medicineName, String username);
	
	List<Medicine> findAllByUserId(Long id);
	
	void deleteByUserId(Long userId);

}
