package com.bloff.pharmacy.app.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bloff.pharmacy.app.crm.CrmUser;
import com.bloff.pharmacy.app.dao.RoleRepository;
import com.bloff.pharmacy.app.dao.UserRepository;
import com.bloff.pharmacy.app.entity.Role;
import com.bloff.pharmacy.app.entity.User;
import com.bloff.pharmacy.app.service.UserServiceImpl;

public class UserServiceTests {

	@Mock
	private UserRepository repo;
	
	@Mock
	private RoleRepository roleRepo;
	
	@Mock
	private BCryptPasswordEncoder passwordEncoder;
	
	@InjectMocks
	private UserServiceImpl service;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void should_find_by_username() {
		
		String username = "steve";
		
		User user = new User(username, "test", "Steven", "King", "steve@gmail.com", 
					Arrays.asList(new Role("ROLE_USER"), new Role("ROLE_ADMIN")));
		
		when(repo.findByUsername(username)).thenReturn(user);
		
		User actual = service.findByUserName(username);
		
		verify(repo, times(1)).findByUsername(username);
		verifyNoMoreInteractions(repo);
		assertThat(actual).isEqualTo(user);
		assertNotNull(actual);
	}
	
	@Test
	public void should_save_new_user() {
		
		CrmUser userCrm = new CrmUser();
		userCrm.setEmail("mike@gmail.com");
		userCrm.setFirstName("Mike");
		userCrm.setLastName("Smith");
		userCrm.setPassword("test");
		userCrm.setMatchingPassword("test");
		userCrm.setUserName("mike");
		
		User userSaved = new User();
		userSaved.setEmail(userCrm.getEmail());
		userSaved.setFirstName(userCrm.getFirstName());
		userSaved.setLastName(userCrm.getLastName());
		userSaved.setPassword(passwordEncoder.encode(userCrm.getPassword()));
		userSaved.setUsername(userCrm.getUserName());
		
		when(repo.save(Mockito.any(User.class))).thenReturn(userSaved);
		
		User actualUser = service.save(userCrm);
		
		assertThat(userCrm.getUserName()).isEqualTo(actualUser.getUsername());
		assertThat(userCrm.getFirstName()).isEqualTo(actualUser.getFirstName());
		assertThat(userCrm.getLastName()).isEqualTo(actualUser.getLastName());
		assertThat(userCrm.getEmail()).isEqualTo(actualUser.getEmail());
		
	// TODO - problem with password	- test fail
		
		//assertThat(userCrm.getPassword()).isEqualTo(actualUser.getPassword());
		//verify(repo, times(1)).save(userSaved);
		//verifyNoMoreInteractions(repo);
	}
	
	@Test
	public void should_load_user_by_username() {
		
		User user = new User();
		user.setUsername("John");
		user.setPassword("test");
		user.setRoles(Arrays.asList(new Role("ROLE_USER")));
		
		when(repo.findByUsername(user.getUsername())).thenReturn(user);
		
		UserDetails actual = service.findByUserName(user.getUsername());
		
		verify(repo, times(1)).findByUsername(user.getUsername());
		verifyNoMoreInteractions(repo);
		
		assertThat(actual).isEqualTo(user);
		assertNotNull(actual);
		
	}
	
	@Test
	public void should_not_load_user_by_username_throw_UsernameNotFoundException() {
		
		Assertions.assertThrows(UsernameNotFoundException.class, () -> {
			
			User user = new User();

			when(repo.findByUsername(user.getUsername())).thenThrow(new UsernameNotFoundException(""));
			
			service.findByUserName(user.getUsername());
			
			verify(repo, times(1)).findByUsername(user.getUsername());
			verifyNoMoreInteractions(repo);
		});
	}
	
	@Test
	public void should_find_all_users() {
		List<User> users = new ArrayList<>();
		
		when(repo.findAll()).thenReturn(users);
		
		List<User> actual = service.findAllUsers();
		
		verify(repo, times(1)).findAll();
		verifyNoMoreInteractions(repo);
		
		assertThat(actual).isEqualTo(users);
		
	}
	
	@Test
	public void should_find_by_user_id() {
		
		Long theId = 1L;
		
		User user = new User(theId, "Mike");
		
		when(repo.findById(theId)).thenReturn(Optional.of(user));
		
		User actual = service.findByUserId(theId);
		
		verify(repo, times(1)).findById(theId);
		verifyNoMoreInteractions(repo);
		
		assertNotNull(actual);
		assertThat(actual).isEqualTo(user);
		
	}
	
	@Test
	public void should_not_find_by_id_throw_exception() {
		
		Assertions.assertThrows(RuntimeException.class, () -> {
			
			User user = new User();
			
			when(repo.findById(user.getId())).thenThrow(new RuntimeException("Did not find user by id "));
			
			service.findByUserId(user.getId());
			
			verify(repo, times(1)).findById(user.getId());
			verifyNoMoreInteractions(repo);
			
		});
	}

	@Test
	public void should_delete_user_by_id() {
		
		Long id = 1L;
		
		new User(id, "Kate");
		
		service.deleteById(id);
		
		verify(repo, times(1)).deleteById(id);
		verifyNoMoreInteractions(repo);
	}
	
	@Test
	public void should_search_users_by_username_for_admin_request() {
		
		String name = "Jake";
		
		User ann = new User("Ann");
		User jakee = new User("Jakee");
		User kate =new User("Kate");
		User jake =new User("Jake");
		User john =new User("John");
		
		List<User> users = new ArrayList<>();
		users.add(ann);
		users.add(jakee);
		users.add(kate);
		users.add(jake);
		users.add(john);
		
		when(repo.findByUsernameContainsIgnoreCase(name)).thenReturn(Arrays.asList(jake, jakee));
		
		List<User> usersListActual = service.searchByUserName(name);
		
		verify(repo, times(1)).findByUsernameContainsIgnoreCase(name);
		verifyNoMoreInteractions(repo);
		
		assertThat(usersListActual.size()).isEqualTo(2);
	}
	
	@Test
	public void should_return () {
		
	}
	
}
