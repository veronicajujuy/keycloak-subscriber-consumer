package com.dh.paymentservice.repository;

import com.dh.paymentservice.model.SubscriptionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;


@Repository
public class SubscriptionRepository {
    private FeignSubscriptionRepository feignSubscriptionRepository;
    
    public SubscriptionRepository(FeignSubscriptionRepository feignSubscriptionRepository) {
        this.feignSubscriptionRepository = feignSubscriptionRepository;
    }

    public SubscriptionDTO findByUserId(Integer userId) {
        ResponseEntity<SubscriptionDTO> response = feignSubscriptionRepository.findByUserId(userId);
        return response.getBody();
    }


}
