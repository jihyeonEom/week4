package org.mjulikelion.memomanagement.service;

import lombok.AllArgsConstructor;
import org.mjulikelion.memomanagement.domain.User;
import org.mjulikelion.memomanagement.dto.UserCreateDto;
import org.mjulikelion.memomanagement.dto.UserUpdateDto;
import org.mjulikelion.memomanagement.dto.response.UserListResponseData;
import org.mjulikelion.memomanagement.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(UserCreateDto userCreateDto, String userId) {
        User user = new User(userId, userCreateDto.getName());
        this.userRepository.createUser(user);
    }

    public UserListResponseData getAllUsers() {
        return new UserListResponseData(userRepository.getAllUsers());
    }

    public void deleteUserByUserId(String userId) {
        this.userRepository.deleteUserByUserId(userId);
    }

    public void updateUserByUserId(UserUpdateDto userUpdateDto, String userId) {
        this.userRepository.updateUserByUserId(userId, userUpdateDto.getName());
    }
}
