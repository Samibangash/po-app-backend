package com.poapp.config;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.poapp.model.User;
import com.poapp.repository.UserRepository;

@Configuration
public class UserSeeder {

    @Bean
    ApplicationRunner init(UserRepository userRepository) {
        return args -> {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            // Check if the users already exist before seeding
            if (!userRepository.existsByUsername("team_lead")) {
                User teamLead = new User();
                teamLead.setUsername("team_lead");
                teamLead.setPassword(passwordEncoder.encode("password123"));
                teamLead.setRole("Team Lead");
                userRepository.save(teamLead);
            }

            if (!userRepository.existsByUsername("department_manager")) {
                User departmentManager = new User();
                departmentManager.setUsername("department_manager");
                departmentManager.setPassword(passwordEncoder.encode("password123"));
                departmentManager.setRole("Department Manager");
                userRepository.save(departmentManager);
            }

            if (!userRepository.existsByUsername("finance_manager")) {
                User financeManager = new User();
                financeManager.setUsername("finance_manager");
                financeManager.setPassword(passwordEncoder.encode("password123"));
                financeManager.setRole("Finance Manager");
                userRepository.save(financeManager);
            }
        };
    }
}
