package com.bloff.pharmacy.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bloff.pharmacy.app.dao.ProducentRepository;
import com.bloff.pharmacy.app.entity.Producent;

@Service
public class ProducentServiceImpl implements ProducentService {

	@Autowired
	private ProducentRepository producentDao;
	
	@Override
	public List<Producent> findAll() {
		return producentDao.findAll();
	}

	@Override
	public Producent findById(Long producentId) {
		
		Optional<Producent> result = producentDao.findById(producentId);
		
		Producent producent = null;
		
		if (result.isPresent()) {
			producent = result.get();
		} else {
			throw new RuntimeException("Didn't find producent by id = " + producentId);
		}
		
		return producent;
	}

	@Override
	public Producent save(Producent producent) {

		return producentDao.save(producent);
	}

	@Override
	public void deleteById(Long producentId) {

		producentDao.deleteById(producentId);
	}

	@Override
	public List<Producent> findAllByUserId(Long userId) {
		
		return producentDao.findAllByUserId(userId);
	}

	@Override
	public void deleteByUserId(Long userId) {

		producentDao.deleteAllByUserId(userId);
		
	}

	/*@Override
	public Producent findByUserId(Long userId) {

		Optional<Producent> result = producentDao.findByUserId(userId);
		
		Producent producent = null;
		if(result.isPresent()) {
			producent = result.get();
		} else {
			throw new RuntimeException("Didn't find producent by userId: " + userId);
		}
		return producent;
	}*/

}
