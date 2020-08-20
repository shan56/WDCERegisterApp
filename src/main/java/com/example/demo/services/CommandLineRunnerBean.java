package com.example.demo.services;

import com.example.demo.models.Role;
import com.example.demo.interfaces.RoleRepository;
import com.example.demo.models.User;
import com.example.demo.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerBean implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public void run(String... args) {
        User admin = new User("super", "super@domain.com", "", "Super", "Hero", true);
        Role adminRole = new Role("super", "ROLE_ADMIN");
        userRepository.save(admin);
        roleRepository.save(adminRole);

    }
}
