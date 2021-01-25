package com.bloff.pharmacy.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bloff.pharmacy.app.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findRoleByName(String theRoleName);

	
}
