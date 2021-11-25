package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	@Autowired
	private UserRepository repo;
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateNewUserWithOneRole() {
		Role roleAdmin=entityManager.find(Role.class, 1);
		User userAtanu= new User("atanuchowdhury@gmail.com","atanu2021","Atanu","Chowdhury"); 
		userAtanu.addRole(roleAdmin);
		User savedUser=repo.save(userAtanu);
		assertThat(savedUser.getId()).isGreaterThan(0);
		
	}
	@Test
	public void testCreateNewUserWithTwoRoles() {
		User userRavi = new User("ravi21@gmail.com","ravi2021","Ravi","Sharma");
		Role roleEditor=new Role(3);
		Role roleAssistant=new Role(5);
		userRavi.addRole(roleEditor);
		userRavi.addRole(roleAssistant);
		User savedUser=repo.save(userRavi);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers=repo.findAll();
		listUsers.forEach(user->System.out.println(user));
	}
	@Test
	public void testGetUserById() {
		User userAtanu=repo.findById(1).get();
		System.out.println(userAtanu);
		assertThat(userAtanu).isNotNull();
	}
	
	@Test void testUpdateUserDetails() {
		User userAtanu=repo.findById(1).get();
		userAtanu.setEnabled(true);
		userAtanu.setEmail("IamAtanu@gmail.com");
		repo.save(userAtanu);
		
	}
	@Test
	public void testUpadateRoles() {
		User userRavi=repo.findById(2).get();
		Role roleEditor=new Role(3);
		Role roleSalesperson=new Role(2);
		userRavi.getRoles().remove(roleEditor);
		userRavi.addRole(roleSalesperson);
		repo.save(userRavi);
		
	}
	@Test
	public void testDeleteUser() {
		Integer userId=2;
		repo.deleteById(userId);
		
	}
	@Test
	public void testGetUserByEmail() {
		String email="rohitRoy@hotmail.com";
		User user= repo.getUserByEmail(email);
		assertThat(user).isNotNull();
		
	}

}
