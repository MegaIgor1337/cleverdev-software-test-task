package com.lam.migrationservice.service;


import com.lam.migrationservice.model.User;

import java.util.Map;
import java.util.Set;

public interface UserService {
    Map<String, User> getLoginUserMap(Set<String> logins);
}
