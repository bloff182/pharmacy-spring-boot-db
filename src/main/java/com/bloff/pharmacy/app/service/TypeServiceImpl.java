package com.bloff.pharmacy.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bloff.pharmacy.app.dao.TypeRepository;
import com.bloff.pharmacy.app.entity.Type;

@Service
public class TypeServiceImpl implements TypeService {

	@Autowired
	private TypeRepository typeDao;
	
	@Override
	public List<Type> findAll() {
		return typeDao.findAll();
	}

	@Override
	public Type findById(Long typeId) {
		
		Optional<Type> result = typeDao.findById(typeId);
		
		Type type = null;
		
		if (result.isPresent()) {
			type = result.get();
		} else {
			
			throw new RuntimeException("Didn't find type by id = " + typeId);
		}
		return type;
	}

	@Override
	public Type save(Type theType) {
		
		return typeDao.save(theType);
	}

	@Override
	public void deleteById(Long typeId) {

		typeDao.deleteById(typeId);
	}

}
