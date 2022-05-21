package com.example.advancedproject;

import com.example.advancedproject.Entities.Role;
import com.example.advancedproject.Entities.UserEntity;
import com.example.advancedproject.Repos.UserRepository;
import com.example.advancedproject.Services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class AdvancedProjectApplication {

    public static void main(String[] args){
        SpringApplication.run(AdvancedProjectApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
//    http://localhost:8080/
//    @Bean
//    CommandLineRunner run(UserService userService){
//        return args -> {
//            userService.saveRole(new Role(null, "ROLE_USER"));
//            userService.saveRole(new Role(null, "ROLE_ADMIN"));
//
//            userService.register(new UserEntity("Olzhas", "Baisakov", "ol@gmail.com", "123123", new ArrayList<>()));
//            userService.register(new UserEntity("Olzhas", "Ibragimov", "olzhas2106@gmail.com", "1423", new ArrayList<>()));
//            userService.register(new UserEntity("Baida", "Baida", "Baida@gmail.com", "1987", new ArrayList<>()));
//            userService.register(new UserEntity("Sanzhik", "Sanzhik", "Sanzhik@gmail.com", "9876", new ArrayList<>()));
//
//            userService.addRoleToUser("ol@gmail.com","ROLE_USER");
//            userService.addRoleToUser("olzhas2106@gmail.com","ROLE_ADMIN");
//            userService.addRoleToUser("olzhas2106@gmail.com","ROLE_USER");
//            userService.addRoleToUser("Baida@gmail.com","ROLE_USER");
//            userService.addRoleToUser("Sanzhik@gmail.com","ROLE_USER");
//        };
//    }
}
