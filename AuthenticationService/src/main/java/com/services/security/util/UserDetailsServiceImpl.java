package com.services.security.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("customUserDetailsService")
public class UserDetailsServiceImpl implements  UserDetailsService{

    private static Map<String, String> users = new HashMap<>();

    static {

        users.put("product-service", "P&ssw0rd");
        users.put("order-service", "P&$$w0rd");
        users.put("inventory-service", "Pa$$w0rd");
        users.put("user-service", "P&$sw0rd");
        users.put("payment-service", "P&$$word");

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String password = users.get(username);
        if (password == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return User.withUsername(username)
                .password(new BCryptPasswordEncoder().encode(password))
                .authorities("USER")
                .build();
    }

}

