package com.bloff.pharmacy.app.test.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.bloff.pharmacy.app.dao.MedicineRepository;
import com.bloff.pharmacy.app.dao.UserRepository;
import com.bloff.pharmacy.app.entity.Medicine;
import com.bloff.pharmacy.app.entity.User;

@DataJpaTest
public class MedicineRepositoryTests {

	@Autowired
	private MedicineRepository medRepo;

	@Autowired
	private UserRepository userRepo;
	
	@BeforeEach
	public void setUp() {
		loadData();
	}
	
	@Test
	public void should_find_medicines_by_username() {
		
		String username = "Mike";
		
		List<Medicine> medicines = medRepo.findAllByUser_Username(username);
		
		assertThat(medicines.size()).isEqualTo(2);
		
		
	}
	

	private void loadData() {

		List<Medicine> medicines = Arrays.asList(
				new Medicine("controloc", LocalDate.of(2021, 12, 15),2, createUsers().get(0)),
				new Medicine("sambucus", LocalDate.of(2021, 9, 22),5, createUsers().get(1)),
				new Medicine("dicoflor", LocalDate.of(2021, 11, 25),2, createUsers().get(2)),
				new Medicine("glucophage", LocalDate.of(2021, 6, 5),4, createUsers().get(1)),
				new Medicine("juvit c", LocalDate.of(2021, 5, 25),3, createUsers().get(2)),
				new Medicine("vigantol", LocalDate.of(2021, 10, 9),1, createUsers().get(0)));
		
		for(Medicine m : medicines)
			medRepo.save(m);
			
	}
	
	private List<User> createUsers() {
		List<User> users = Arrays.asList(
				new User("Mike"),
				new User("Ann"),
				new User("Kate"));

		return users;
	}
}
