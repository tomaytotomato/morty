package com.kapango.infra.seeder;

import static com.kapango.infra.entity.user.UserAccountType.ADMIN;

import com.kapango.infra.config.MortyInfraConfig;
import com.kapango.infra.entity.user.UserEntity;
import com.kapango.infra.repository.user.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Creates the specified Admin account as defined in {@link MortyInfraConfig} if it doesn't already exist
 */
@Component
@Slf4j(topic = "Admin Seeder")
public class AdminSeeder {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    private final MortyInfraConfig config;
    public AdminSeeder(UserDao userDao, PasswordEncoder passwordEncoder, MortyInfraConfig config) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.config = config;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        log.info("Checking if an Admin account with username {} exists", config.getAdminUsername());
        if (userDao.findByUsername(config.getAdminUsername()).isEmpty()) {
            log.info("No Admin account with username {} found", config.getAdminUsername());
            var username = config.getAdminUsername();
            var password = config.getAdminPassword();;
            log.info("Creating an Admin account with username {} and password {}", username, password);
            var newAdmin = UserEntity.builder()
                .username(username)
                .firstName("Admin")
                .lastName("User")
                .accountType(ADMIN)
                .hashedPassword(passwordEncoder.encode(password))
                .build();
            userDao.save(newAdmin);
        }
    }
}