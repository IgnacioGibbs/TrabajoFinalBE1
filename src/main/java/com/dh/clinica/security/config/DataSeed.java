package com.dh.clinica.security.config;

import com.dh.clinica.security.entity.AppUser;
import com.dh.clinica.security.enums.AppUserRole;
import com.dh.clinica.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeed implements ApplicationRunner {
    private final UserRepository userRepository;

    @Autowired
    public DataSeed(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void run(ApplicationArguments args) {

        if (userRepository.count() > 0) {
            return;
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode("PepitoPass");
        BCryptPasswordEncoder passwordEncoder2 = new BCryptPasswordEncoder();
        String hashedPassword2 = passwordEncoder2.encode("CuchuflitoPass");
        userRepository.save(new AppUser("Pepito", "Pepito", "pepito@gmail.com", hashedPassword, AppUserRole.ADMIN));
        userRepository.save(new AppUser("Cuchuflito", "Cuchuflito", "cuchuflito@gmail.com", hashedPassword2, AppUserRole.USER));
    }
}
