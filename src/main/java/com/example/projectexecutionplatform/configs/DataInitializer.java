package com.example.projectexecutionplatform.configs;

import com.example.projectexecutionplatform.models.entities.Users;
import com.example.projectexecutionplatform.models.enums.Roles;
import com.example.projectexecutionplatform.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception {

        // Seeded Admin details :
        if(userRepository.findByEmail(adminEmail).isEmpty()){

            Users admin = Users.builder()
                    .email(adminEmail)
                    .password(passwordEncoder.encode(adminPassword))  //Have to bcrypt this pass
                    .role(Roles.ADMIN)
                    .createdAt(LocalDateTime.now())
                    .build();

            userRepository.save(admin);
            System.out.println("Default ADMIN created : "+ adminEmail);
        }
        else{
            System.out.println("Admin Already Exists");
        }

    }
}
