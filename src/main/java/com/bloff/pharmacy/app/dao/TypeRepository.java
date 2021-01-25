package com.bloff.pharmacy.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bloff.pharmacy.app.entity.Type;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {

	Type findByTypeName(String name);
	
}
