package com.bloff.pharmacy.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bloff.pharmacy.app.entity.Destiny;

@Repository
public interface DestinyRepository extends JpaRepository<Destiny, Long> {

	Destiny findByDestinyName(String name);
}
