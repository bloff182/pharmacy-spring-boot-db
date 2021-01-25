package com.bloff.pharmacy.app.service;

import java.util.List;

import com.bloff.pharmacy.app.entity.Type;

public interface TypeService {

	public List<Type> findAll();
	
	public Type findById(Long typeId);
	
	public Type save(Type theType);
	
	public void deleteById(Long typeId);
	
}
