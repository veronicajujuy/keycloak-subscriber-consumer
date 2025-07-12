package com.dh.subscriptionservice.controller;

import com.dh.subscriptionservice.model.Subscription;
import com.dh.subscriptionservice.service.ISubscriptionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/subscription")
public class SubscriptionRestController {

    private ISubscriptionService subscriptionService;

    @Value("${server.port}")
    private int serverPort;

    public SubscriptionRestController(ISubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/find")
    @PreAuthorize("hasRole('ROLE_admin_users')")
    public Subscription findSubscriptionByUser(@RequestParam Integer userId, HttpServletResponse response) {
        response.addHeader("port", String.valueOf(serverPort));
        return subscriptionService.findSubscriptionByUser(userId);
    }

}
