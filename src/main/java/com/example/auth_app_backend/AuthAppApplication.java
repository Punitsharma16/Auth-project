package com.example.auth_app_backend;

import com.example.auth_app_backend.config.APPConstants;
import com.example.auth_app_backend.entities.Role;
import com.example.auth_app_backend.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class AuthAppApplication implements CommandLineRunner {
	@Autowired
private RoleRepository roleRepository;


	public static void main(String[] args) {
		SpringApplication.run(AuthAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// we  will create some default user Roles
		//Admin
		//Guest
		roleRepository.findByName("ROLE"+APPConstants.ADMIN_ROLE).ifPresentOrElse(role ->{
		},()->{
			Role role=new Role();
			role.setName("ROLE"+APPConstants.ADMIN_ROLE);
			role.setId(UUID.randomUUID());
			roleRepository.save(role);
		});
		roleRepository.findByName("ROLE"+APPConstants.GUEST_ROLE).ifPresentOrElse(role ->{
		},()->{
			Role role=new Role();
			role.setName("ROLE"+APPConstants.GUEST_ROLE);
			role.setId(UUID.randomUUID());
			roleRepository.save(role);
		});
	}



}
