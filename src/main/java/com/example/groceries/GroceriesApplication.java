package com.example.groceries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.groceries.security.entity.Role;
import com.example.groceries.security.entity.User;
import com.example.groceries.security.repository.UserRepository;

@SpringBootApplication
public class GroceriesApplication implements CommandLineRunner{

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(GroceriesApplication.class, args);
		
	}

	public void run(String... args) {
		User adminAccount = userRepository.findByRole(Role.ADMIN);
		if (null == adminAccount) {
			User user = new User();
			user.setEmail("kahandy2@gmail.com");
			user.setUsername("admin");
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			user.setRole(Role.ADMIN);

			userRepository.save(user);
		}
	}

}
