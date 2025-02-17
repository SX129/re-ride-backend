package com.re_ride.userms.user.impl;

import com.re_ride.userms.user.User;
import com.re_ride.userms.user.UserRepository;
import com.re_ride.userms.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public List<User> getAllRiders() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getUserType() == User.UserType.RIDER)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getAllDrivers() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getUserType() == User.UserType.DRIVER)
                .collect(Collectors.toList());
    }

    @Override
    public User createUser(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public User updateUserById(Long userId, User user) {
        if(getUserById(userId) != null){
            User updatedUser = getUserById(userId);

            if(user.getEmail() != null){
                updatedUser.setEmail(user.getEmail());
            }

            if(user.getPhoneNumber() != null){
                updatedUser.setPhoneNumber(user.getPhoneNumber());
            }

            if(user.getFirstName() != null){
                updatedUser.setFirstName(user.getFirstName());
            }

            if(user.getLastName() != null){
                updatedUser.setLastName(user.getLastName());
            }

            if(user.getPassword() != null){
                updatedUser.setPassword(user.getPassword());
            }

            userRepository.save(updatedUser);
            return updatedUser;
        }

        return null;
    }

    @Override
    public String deleteUserById(Long userId) {
        if(getUserById(userId) != null){
            userRepository.deleteById(userId);
            return "User deleted successfully.";
        }

        return "User not found.";
    }
}
