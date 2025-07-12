package com.dh.subscriptionservice.client;

import com.dh.subscriptionservice.model.dto.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;

// En lugar de usar la anotación FeignClient, usaremos una implementación manual temporalmente
@Component
public class UserFeignClient {

    public ResponseEntity<UserDTO> findById(Integer id) {
        // Implementación temporal que devuelve null
        return ResponseEntity.notFound().build();
    }
}
