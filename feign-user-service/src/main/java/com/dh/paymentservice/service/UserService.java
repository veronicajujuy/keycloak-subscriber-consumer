package com.dh.paymentservice.service;

import com.dh.paymentservice.model.SubscriptionDTO;
import com.dh.paymentservice.model.User;
import org.springframework.stereotype.Service;

import com.dh.paymentservice.repository.SubscriptionRepository;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private final List<User> userRepository;
    private SubscriptionRepository subscriptionRepository;

    public UserService(List<User> userRepository, SubscriptionRepository subscriptionRepository) {
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public User findById(Integer id) {
        User user = userRepository.stream().filter(_user -> Objects.equals(_user.getId(), id)).findFirst().orElse(null);
        SubscriptionDTO subscriptionDTO = subscriptionRepository.findByUserId(id);
        if (user != null){
            user.setSubscription(subscriptionDTO);
        }
        return user;
    }
}
