package com.licenta.demo.service;

import com.licenta.demo.entity.Appointment;
import com.licenta.demo.entity.MyUser;
import com.licenta.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<MyUser> getAllUsers() {
        return userRepository.findAll();
    }

    public void saveUser(MyUser user) {
        userRepository.save(user);
    }

    public MyUser findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public boolean deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public MyUser findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
