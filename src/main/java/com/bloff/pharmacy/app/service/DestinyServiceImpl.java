package com.bloff.pharmacy.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bloff.pharmacy.app.dao.DestinyRepository;
import com.bloff.pharmacy.app.entity.Destiny;

@Service
public class DestinyServiceImpl implements DestinyService {

	@Autowired
	private DestinyRepository destinyDao;
	
	@Override
	public List<Destiny> findAll() {
		return destinyDao.findAll();
	}

	@Override
	public Destiny findById(Long destinyId) {
		
		Optional<Destiny> result = destinyDao.findById(destinyId);
		
		Destiny destiny = null;
		
		if (result.isPresent()) {
			destiny = result.get();
		} else {
			throw new RuntimeException("Didn't find destiny by id = " + destinyId);
		}
		
		return destiny;
	}

	@Override
	public Destiny save(Destiny destiny) {

		return destinyDao.save(destiny);
	}

	@Override
	public void deleteById(Long destinyId) {

		destinyDao.deleteById(destinyId);
	}


}
