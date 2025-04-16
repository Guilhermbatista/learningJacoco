package com.devsuperior.dscommerce.tests;

import java.time.LocalDate;

import com.devsuperior.dscommerce.entities.Role;
import com.devsuperior.dscommerce.entities.User;

public class UserFactory {
	
	public static User createClientUser() {
		User user = new User(1L, "Maria", "maria@gmail.com", "988888889",LocalDate.parse("2001-07-25") ,"$2a$10$N7SkKCa3r17ga.i.dF9iy.BFUBL2n3b6Z1CWSZWi/qy7ABq/E6VpO");
		user.addRole(new Role(1L, "ROLE_CLIENT"));
		return user;
	}
	public static User createAdminUser() {
		User user = new User(2L, "Alex", "alex@gmail.com", "9898989854",LocalDate.parse("2002-07-25") ,"$2a$10$N7SkKCa3r17ga.i.dF9iy.BFUBL2n3b6Z1CWSZWi/qy7ABq/E6VpO");
		user.addRole(new Role(2L, "ROLE_ADMIN"));
		return user;
	}

	public static User createCustomAdminUser(Long id, String username) {
		User user = new User(id, username, "alex@gmail.com", "9898989854",LocalDate.parse("2002-07-25") ,"$2a$10$N7SkKCa3r17ga.i.dF9iy.BFUBL2n3b6Z1CWSZWi/qy7ABq/E6VpO");
		user.addRole(new Role(2L, "ROLE_ADMIN"));
		return user;
	}
	public static User createCustomClientUser(Long id, String username) {
		User user = new User(id, username, "alex@gmail.com", "9898989854",LocalDate.parse("2002-07-25") ,"$2a$10$N7SkKCa3r17ga.i.dF9iy.BFUBL2n3b6Z1CWSZWi/qy7ABq/E6VpO");
		user.addRole(new Role(1L, "ROLE_CLIENT"));
		return user;
	}
	
}
