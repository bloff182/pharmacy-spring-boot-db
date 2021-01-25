package com.bloff.pharmacy.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bloff.pharmacy.app.entity.Producent;

@Repository
public interface ProducentRepository  extends JpaRepository<Producent, Long> {

	List<Producent> findAllByUserId(Long userId);
	
	void deleteAllByUserId(Long userId);

//	Optional<Producent> findByUserId(Long userId);
}
