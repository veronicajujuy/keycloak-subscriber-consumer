package com.dh.subscriptionservice.service;

import com.dh.subscriptionservice.model.Subscription;

public interface ISubscriptionService {
    Subscription findSubscriptionByUser(Integer userId);
}
