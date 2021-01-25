package com.bloff.pharmacy.app.test.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.bloff.pharmacy.app.dao.TypeRepository;
import com.bloff.pharmacy.app.entity.Type;

@DataJpaTest(showSql=false)
//@AutoConfigureTestDatabase(replace=Replace.NONE) // work on real db like mySQL
//@TestPropertySource(locations="classpath:test.properties")
public class TypeRepositoryTests {

	Type testType;
	
	@Autowired
	private TypeRepository typeRepo;
	
	@BeforeEach
	public void setUp() {
		testType = typeRepo.save(new Type("tabletki"));

	}
	
	@Test
	public void should_save_type() {
		
		Type testTypeSaved = typeRepo.save(new Type("tabs"));
		
		assertThat(testTypeSaved).isNotNull();
		assertTrue(testTypeSaved.getId()>0);
	}
	
	@Test
	public void should_find_type_by_name_exist() {
		
		String name = "tabletki";
		
		Type theType = typeRepo.findByTypeName(name);
		
		assertNotNull(theType);
		assertThat(theType.getTypeName()).isEqualTo(name);
		
	}
	
	@Test
	public void should_find_type_by_name_not_exist() {
		
		String name = "kropelki";
		
		Type theType = typeRepo.findByTypeName(name);
		
		assertNull(theType);
		
	}
	
	@Test
	public void should_update_type() {
	
		// init new name of type
		String newName = "kapsy";
		Type newType = new Type(newName);
		
		// changing exist type on new type 
		newType.setId(1L);
		
		// saving changes
		typeRepo.save(newType);
		
		assertThat(newType.getTypeName()).isEqualTo(newName);
		
	}
	
	@Test
	public void should_find_all_types() {
		
		// finding all items in db
		List<Type> types = typeRepo.findAll();
		
		types.stream().forEach(System.out::println);
		
		assertThat(types.size()).isGreaterThan(0);
		
	}
	
	@Test
	public void should_delete_type_by_id() {
		
		// getting type's id to delete
		Long id = testType.getId();
		
		// checking exist type
		boolean beforeDel = typeRepo.findById(id).isPresent();
		
		// deleting type
		typeRepo.deleteById(id);

		// checking exist type
		boolean afterDel = typeRepo.findById(id).isPresent();
		
		assertTrue(beforeDel);
		assertFalse(afterDel);
		
	}
	
}
