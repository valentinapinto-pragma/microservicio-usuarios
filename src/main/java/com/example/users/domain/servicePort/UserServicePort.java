package com.example.users.domain.servicePort;

import com.example.users.domain.model.User;

public interface UserServicePort {
    void createOwner(User user);
    User getUserById(Long id);
    void createEmployee(User user);
    void createClient(User user);


}
