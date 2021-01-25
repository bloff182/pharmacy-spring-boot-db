package com.bloff.pharmacy.app.service;

import java.util.List;

import com.bloff.pharmacy.app.entity.Producent;

public interface ProducentService {

	public List<Producent> findAll();
	
	public Producent findById(Long producentId);
	
	public Producent save(Producent producent);
	
	public void deleteById(Long producentId);

	public List<Producent> findAllByUserId(Long userId);

	public void deleteByUserId(Long userId);

//	public Producent findByUserId(Long userId);
	
}
