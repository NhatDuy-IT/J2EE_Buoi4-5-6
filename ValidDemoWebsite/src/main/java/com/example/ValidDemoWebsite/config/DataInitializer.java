package com.example.ValidDemoWebsite.config;

import com.example.ValidDemoWebsite.model.Account;
import com.example.ValidDemoWebsite.model.Role;
import com.example.ValidDemoWebsite.repository.AccountRepository;
import com.example.ValidDemoWebsite.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner seedData(AccountRepository accountRepository,
                                      RoleRepository roleRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            Role userRole = roleRepository.findByName("USER").orElseGet(() -> roleRepository.save(new Role("USER")));
            Role adminRole = roleRepository.findByName("ADMIN").orElseGet(() -> roleRepository.save(new Role("ADMIN")));

            if (accountRepository.findByLoginName("user").isEmpty()) {
                Account user = new Account();
                user.setLoginName("user");
                user.setPassword(passwordEncoder.encode("123456"));
                Set<Role> roles = new HashSet<>();
                roles.add(userRole);
                user.setRoles(roles);
                accountRepository.save(user);
            }

            if (accountRepository.findByLoginName("admin").isEmpty()) {
                Account admin = new Account();
                admin.setLoginName("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                Set<Role> roles = new HashSet<>();
                roles.add(adminRole);
                admin.setRoles(roles);
                accountRepository.save(admin);
            }
        };
    }
}
