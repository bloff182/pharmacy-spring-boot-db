package com.bloff.pharmacy.app.test.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.bloff.pharmacy.app.dao.ProducentRepository;
import com.bloff.pharmacy.app.entity.Producent;

@DataJpaTest
public class ProducentRepositoryTests {
	
	@Autowired
	private ProducentRepository repo;
	
	@BeforeEach
	public void setUp() {
		loadData();
	}
	
	@Test
	public void should_find_all_producents_by_userId_exist() {
		
		Long userId = 2L;
		
		List<Producent> producents = repo.findAllByUserId(userId);
		
		assertThat(producents.size()).isEqualTo(3);
		
	}
	
	@Test
	public void should_find_all_producents_by_userId_not_exist() {
		
		Long userId = 3L;
		
		List<Producent> producents = repo.findAllByUserId(userId);
		
		assertThat(producents.size()).isEqualTo(0);
		
	}
	
	
	@Test
	public void should_delete_all_producents_by_userId_exist() {
		Long userId = 2L;
		
		List<Producent> producentsBeforeDel = repo.findAllByUserId(userId);
		
		repo.deleteAllByUserId(userId);
		
		List<Producent> producentsAfterDel = repo.findAllByUserId(userId);
		
		assertThat(producentsBeforeDel.size()).isEqualTo(3);
		assertThat(producentsAfterDel).isEmpty();
		
	}
	
	@Test
	public void should_delete_all_producents_by_userId_not_exist() {
		Long userId = 4L;
		
		List<Producent> producentsBeforeDel = repo.findAllByUserId(userId);
		
		repo.deleteAllByUserId(userId);
		
		List<Producent> producentsAfterDel = repo.findAllByUserId(userId);
		
		assertThat(producentsBeforeDel).isEmpty();
		assertThat(producentsAfterDel).isEmpty();
		
	}
	
	private void loadData() {
		List<Producent> producents = Arrays.asList(
				new Producent("Jelfa", 1L, 1, null),
				new Producent("Polfa", 2L, 1, null),
				new Producent("Polfa", 1L, 1, null),
				new Producent("Jelfa", 2L, 1, null),
				new Producent("Bayer", 1L, 1, null),
				new Producent("Hasco", 2L, 1, null));
		
		for(Producent p : producents)
			repo.save(p);
	}
}
