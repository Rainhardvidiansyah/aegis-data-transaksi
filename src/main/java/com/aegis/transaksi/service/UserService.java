package com.aegis.transaksi.service;

import com.aegis.transaksi.entity.Users;
import com.aegis.transaksi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users getUsers(String email){
        Optional<Users> users = this.userRepository.getUserByEmail(email);
        return users.get();
    }

    public boolean createActiveuser(String email){
        return this.userRepository.activateUserByEmail(email) != 0;
    }
}
