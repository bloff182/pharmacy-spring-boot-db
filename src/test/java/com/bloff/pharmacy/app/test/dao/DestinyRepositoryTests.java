package com.bloff.pharmacy.app.test.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.bloff.pharmacy.app.dao.DestinyRepository;
import com.bloff.pharmacy.app.entity.Destiny;

@DataJpaTest
public class DestinyRepositoryTests {

	Destiny destinyTest;
	Destiny destinyTest2;
	
	@Autowired
	private DestinyRepository repo;
	
	@BeforeEach
	void setUp() {
		
		destinyTest = repo.save(new Destiny("Baby"));
		destinyTest2 = repo.save(new Destiny("Man"));
		
	}
	
	@Test
	void should_save_destiny() {
		
		Destiny savedDestiny = repo.save(new Destiny("Adult"));
		
		assertThat(savedDestiny).isNotNull();
		assertThat(savedDestiny.getId()).isGreaterThan(0);
		
	}
	
	@Test
	void should_find_destiny_by_name_exist() {
		
		String name = "Baby";
		
		Destiny checkDestiny = repo.findByDestinyName(name);
		
		assertThat(checkDestiny).isNotNull();
		assertThat(checkDestiny.getDestinyName()).isEqualTo(name);
		
	}
	
	@Test
	void should_find_destiny_by_name_not_exist() {
		
		String name = "All";
		
		Destiny checkDestiny = repo.findByDestinyName(name);
		
		assertThat(checkDestiny).isNull();
	}
	
	@Test
	void should_find_all_destinies() {
		
		// finding all items in db
		List<Destiny> destinies = repo.findAll();
		
		destinies.stream().forEach(System.out::println);
		
		assertThat(destinies.size()).isGreaterThan(0);
		
	}
	
	@Test
	void should_update_destiny() {
		
		String name = "Woman";
		
		// finding destiny name of Man
		Destiny beforeUpdate = repo.findByDestinyName("Man");
		
		// changing destiny name
		beforeUpdate.setDestinyName(name);
		
		// saving changes
		Destiny afterUpdate = repo.save(beforeUpdate);
		
		assertThat(afterUpdate.getDestinyName()).isEqualTo(name);
	}
	
	@Test
	void should_delete_destiny_by_id() {
		
		// getting type's id to delete
		Long id = destinyTest.getId();
		
		// checking exist destiny
		boolean beforeDelete = repo.findById(id).isPresent();
		
		repo.deleteById(id);
		
		// checking exist destiny
		boolean afterDelete = repo.findById(id).isPresent();
		
		assertThat(beforeDelete).isTrue();
		assertThat(afterDelete).isFalse();
	}
	
}
