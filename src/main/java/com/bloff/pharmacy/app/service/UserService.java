package com.bloff.pharmacy.app.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.bloff.pharmacy.app.crm.CrmUser;
import com.bloff.pharmacy.app.entity.User;

public interface UserService extends UserDetailsService {

    public User findByUserName(String username);

    public User save(CrmUser crmUser);

	public List<User> findAllUsers();

	public User findByUserId(Long userId);

	public void deleteById(Long userId);

	public List<User> searchByUserName(String searchUserName);
	
	public User saveForUpdateUser(User theUser);
	
	User findByEmail(String email);

}
