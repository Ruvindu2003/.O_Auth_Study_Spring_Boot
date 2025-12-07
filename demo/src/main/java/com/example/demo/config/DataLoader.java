package com.example.demo.config;

import com.example.demo.dto.User;
import com.example.demo.entity.RoleEntity;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Create default roles if they don't exist
        if (roleRepository.findByUseRole("USER").isEmpty()) {
            RoleEntity userRole = new RoleEntity();
            userRole.setUseRole("USER");
            roleRepository.save(userRole);
        }

        if (roleRepository.findByUseRole("ADMIN").isEmpty()) {
            RoleEntity adminRole = new RoleEntity();
            adminRole.setUseRole("ADMIN");
            roleRepository.save(adminRole);
        }

        // Create default admin user if it doesn't exist
        if (userRepository.findByUsername("admin").isEmpty()) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword("admin");
            
            Set<String> roles = new HashSet<>();
            roles.add("ADMIN");
            adminUser.setRoles(roles);
            
            userService.createUser(adminUser);
        }
    }
}