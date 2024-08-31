package com.lam.migrationservice.service.impl;

import com.lam.migrationservice.model.User;
import com.lam.migrationservice.repository.UserRepository;
import com.lam.migrationservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;



@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Map<String, User> getLoginUserMap(Set<String> logins) {
        List<User> users = userRepository.findAll();
        return users.stream()
                .collect(Collectors.toMap(User::getLogin, Function.identity()));
    }
}
