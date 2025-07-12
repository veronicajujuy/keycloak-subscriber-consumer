package com.dh.paymentservice.configuration;

import com.dh.paymentservice.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class UserConfiguration {

    @Bean
    public List<User> userRepository() {
        return Arrays.asList(
            new User(1, "Juan", "Pérez", "juan.perez@example.com"),
            new User(2, "María", "González", "maria.gonzalez@example.com"),
            new User(3, "Carlos", "Rodríguez", "carlos.rodriguez@example.com"),
            new User(4, "Ana", "López", "ana.lopez@example.com"),
            new User(5, "Pedro", "Martínez", "pedro.martinez@example.com")
        );
    }
}
