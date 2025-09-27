package com.attendance.attendance_tracker;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findAll().isEmpty()) {
            AppUser admin = new AppUser();
            admin.setUsername("atharva-sadavarte-admin");
            admin.setPassword(passwordEncoder.encode("a-s-admin"));
            admin.setRoles(Set.of(Role.ROLE_ADMIN));
            userRepository.save(admin);

            AppUser user = new AppUser();
            user.setUsername("john-bell");
            user.setPassword(passwordEncoder.encode("j-b-user"));
            user.setRoles(Set.of(Role.ROLE_USER));
            userRepository.save(user);
        }
    }
}
