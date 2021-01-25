package com.bloff.pharmacy.app.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bloff.pharmacy.app.crm.CrmUser;
import com.bloff.pharmacy.app.dao.RoleRepository;
import com.bloff.pharmacy.app.dao.UserRepository;
import com.bloff.pharmacy.app.entity.User;

@Service	
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userDao;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public User findByUserName(String userName) {
		// check the database if the user already exists
		return userDao.findByUsername(userName);
	}

	@Override
	@Transactional
	public User save(CrmUser crmUser) {
		
		User user = new User();
		// assign user details to the user object
		user.setUsername(crmUser.getUserName());
		user.setPassword(passwordEncoder.encode(crmUser.getPassword()));
		user.setFirstName(crmUser.getFirstName());
		user.setLastName(crmUser.getLastName());
		user.setEmail(crmUser.getEmail());

		// give user default role of "employee"
		user.setRoles(Arrays.asList(roleRepo.findRoleByName("ROLE_EMPLOYEE")));

		 // save user in the database
		userDao.save(user);

		return user;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		User user = userDao.findByUsername(userName);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		
		user.getUsername();
		user.getPassword();
		user.getRoles();
		
		return user;
	}

	@Override
	public List<User> findAllUsers() {
		return userDao.findAll();
	}

	@Override
	public User findByUserId(Long userId) {
		
		Optional<User> result = userDao.findById(userId);
		
		User theUser = null;
		if(result.isPresent()) {
			theUser= result.get();
		} else
		{
			throw new RuntimeException("Did not find user by id " + userId);
		}
		
		return theUser;
	}

	@Override
	public void deleteById(Long userId) {
		
		userDao.deleteById(userId);
	}

	@Override
	public List<User> searchByUserName(String searchUserName) {

		List <User> results = null;
		
		searchUserName = searchUserName.trim();
		
		if(searchUserName != null && (searchUserName.trim().length()>0)) {
			results = userDao.findByUsernameContainsIgnoreCase(searchUserName);
		} else {
			results = userDao.findAll();
			
		}
		System.out.println(searchUserName.trim());
		return results;
	}

	@Override
	public User saveForUpdateUser(User theUser) {

		return userDao.save(theUser);
		
	}

	public boolean checkExistEmail(String email) {
		
		boolean existEmail = false;
		
		Optional<User> userByEmail = userDao.findByEmail(email);
		
		if(userByEmail.isPresent())
			existEmail = true;
		
		return existEmail;
	}

	@Override
	public User findByEmail(String email) {

		Optional<User> result = userDao.findByEmail(email);
		
		User user = null;
		if(result.isPresent()) {
			user = result.get();
		} else {
			throw new RuntimeException("Didn't find user email: " + email);
		}
		
		return user;
	}
}
