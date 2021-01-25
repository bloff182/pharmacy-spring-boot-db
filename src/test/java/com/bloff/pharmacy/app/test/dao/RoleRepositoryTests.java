package com.bloff.pharmacy.app.test.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.bloff.pharmacy.app.dao.RoleRepository;
import com.bloff.pharmacy.app.entity.Role;

@DataJpaTest
public class RoleRepositoryTests {

	Role role;
	
	@Autowired
	private RoleRepository repo;
	
	@BeforeEach
	public void setUp() {
		role = new Role("ROLE_ADMIN");
		repo.save(role);
	}
	
	@Test
	public void should_find_role_by_name_exist() {
		
		String name = "ROLE_ADMIN";
		
		Role searchRole = repo.findRoleByName(name);
		
		assertThat(searchRole.getName()).isEqualTo(role.getName());
		assertThat(searchRole).isNotNull();
		
	}
	
	@Test
	public void should_find_role_by_name_not_exist() {
		
		String name = "ROLE_BOSS";
		
		Role searchRole = repo.findRoleByName(name);
		
		assertThat(searchRole).isNull();
		
	}
}
