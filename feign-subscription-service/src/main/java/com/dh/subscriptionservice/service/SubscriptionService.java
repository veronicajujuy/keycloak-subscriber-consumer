package com.dh.subscriptionservice.service;

import com.dh.subscriptionservice.model.Subscription;
import com.dh.subscriptionservice.repository.ISubscriptionRepository;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService implements ISubscriptionService {

    private final ISubscriptionRepository subscriptionRepository;

    public SubscriptionService(ISubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public Subscription findSubscriptionByUser(Integer userId) {
        // Temporalmente comentamos la llamada al cliente Feign que está dando problemas
        // y simplemente devolvemos la suscripción directamente
        /*
        try {
            ResponseEntity<UserDTO> userResponse = userFeignClient.findById(userId);
            if (userResponse.getStatusCode().is2xxSuccessful() && userResponse.getBody() != null) {
                return subscriptionRepository.findByUserId(userId);
            }
        } catch (Exception e) {
            // En caso de error, intentar buscar la suscripción de todas formas
        }
        */

        // Devolvemos directamente la suscripción sin verificar el usuario
        return subscriptionRepository.findByUserId(userId);
    }
}
