package org.codewithcuong.hamora.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.codewithcuong.hamora.model.User;
import org.codewithcuong.hamora.repository.UserRepo;

import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepo.getAllUser();
    }

    //  Update role to HOTEL OWNER by user ID
    public void updateUserRoleToHost(int userId) {
        userRepo.updateUserRoleById(userId, "HOTEL OWNER");
    }


}
