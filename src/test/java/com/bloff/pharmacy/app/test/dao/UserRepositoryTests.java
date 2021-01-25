package com.bloff.pharmacy.app.test.dao;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.bloff.pharmacy.app.dao.UserRepository;
import com.bloff.pharmacy.app.entity.Role;
import com.bloff.pharmacy.app.entity.User;

@DataJpaTest
public class UserRepositoryTests {

	@Autowired
	TestEntityManager entityManager;

	@Autowired
	private UserRepository repo;

	@BeforeEach
	public void setUp() {
		
		loadData();
	}

	@Test
	public void should_find_user_by_name_exist() {

		String searchUsername = "user1";

		User searchUser = repo.findByUsername(searchUsername);

		assertThat(searchUser.getUsername()).isEqualTo(searchUsername);
		assertThat(searchUser).isNotNull();
	}
	
	@Test
	public void should_find_user_by_name_not_exist() {

		String searchUsername = "frank";

		User searchUser = repo.findByUsername(searchUsername);

		assertThat(searchUser).isNull();
	}

	@Test
	public void should_find_all_users_by_username_contains_ignoreCase_exist() {

		String searchUsername = "user";
		String searchUsernameUpper = "Ser";
		String searchUsernameEmpty = "";
		
		List<User> users = repo.findByUsernameContainsIgnoreCase(searchUsername);
		List<User> usersUpper = repo.findByUsernameContainsIgnoreCase(searchUsernameUpper);
		List<User> usersEmpty = repo.findByUsernameContainsIgnoreCase(searchUsernameEmpty);
		
		assertThat(users.size()).isEqualTo(3);
		assertThat(usersUpper.size()).isEqualTo(3);
		assertThat(usersEmpty.size()).isEqualTo(5);
	}
	
	@Test
	public void should_find_all_users_by_username_contains_ignoreCase_not_exist() {

		String searchUsername = "tom";
		String searchUsernameUpper = "SLOw";
		

		List<User> users = repo.findByUsernameContainsIgnoreCase(searchUsername);
		List<User> usersUpper = repo.findByUsernameContainsIgnoreCase(searchUsernameUpper);
		

		assertThat(users.size()).isEqualTo(0);
		assertThat(usersUpper.size()).isEqualTo(0);
		
		
	}
	
	@Test
	public void should_find_user_by_email_exist() {
		
		String email = "sam@gmail.com";

		Optional<User> searchUser = repo.findByEmail(email);

		assertThat(searchUser.get().getEmail()).isEqualTo(email);
		assertThat(searchUser).isNotNull();
	}
	
	@Test
	public void should_find_user_by_email_not_exist() {
		
		String email = "sam@gmail.pl";

		Optional<User> searchUser = repo.findByEmail(email);

		assertThat(searchUser).isEmpty();
		
	}

	private void loadData() {
		
		List<Role> roles = Arrays.asList(new Role("ROLE_EMPLOYEE"));

		List<User> listUsers = Arrays.asList(
				new User("user1", "password", "Mike", "Taylor", "mike@gmail.com",
						Arrays.asList(new Role("ROLE_ADMIN"), new Role("ROLE_EMPLOYEE"))),
				new User("user2", "password", "Sam", "Smith", "sam@gmail.com", roles),
				new User("user3", "password", "Tom", "Moon", "tom@gmail.com", roles),
				new User("ann", "password", "Ann", "Sun", "ann@gmail.com",
						Arrays.asList(new Role("ROLE_MANAGER"), new Role("ROLE_EMPLOYEE"))),
				new User("kate", "password", "Kate", "Wind", "kate@gmail.com", roles));

		for (User u : listUsers) {
			repo.save(u);
		}
	}
}
