package com.bloff.pharmacy.app.service;

import java.util.List;

import com.bloff.pharmacy.app.entity.Destiny;

public interface DestinyService {

	public List<Destiny> findAll();
	
	public Destiny findById(Long destinyId);
	
	public Destiny save(Destiny destiny);
	
	public void deleteById(Long destinyId);

	
}
