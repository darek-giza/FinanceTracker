package pl.com.dariusz.giza.warehouse.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.com.dariusz.giza.warehouse.domain.User;
import pl.com.dariusz.giza.warehouse.repositories.UserRepository;

@Component
public class BootStrapData {

    private UserRepository userRepository;

    @Autowired
    public BootStrapData(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void get() {
        User user = new User("user", passwordEncoder().encode("user"), "ROLE_USER");
        User admin = new User("admin", passwordEncoder().encode("admin"), "ROLE_ADMIN");
        userRepository.save(user);
        userRepository.save(admin);

    }
}
