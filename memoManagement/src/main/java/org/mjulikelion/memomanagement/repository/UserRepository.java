package org.mjulikelion.memomanagement.repository;

import lombok.Getter;
import org.mjulikelion.memomanagement.domain.User;
import org.mjulikelion.memomanagement.errorcode.ErrorCode;
import org.mjulikelion.memomanagement.exception.UserAlreadyExistsException;
import org.mjulikelion.memomanagement.exception.UserNotFoundException;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

@RestController
@Getter
public class UserRepository {
    private final WeakHashMap<String, User> users = new WeakHashMap<>();

    public void createUser(User user) {
        for (User u : users.values()) {
            // 유저 아이디 중복 검사
            if (u.getId().equals(user.getId())) {
                throw new UserAlreadyExistsException(ErrorCode.USER_ALREADY_EXISTS);
            }
        }
        users.put(user.getId(), user);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public void deleteUserByUserId(String userId) {
        for (String key : users.keySet()) {
            User user = users.get(key);
            if (user.getId().equals(userId)) {
                users.remove(key);
                return;
            }
        }
        throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
    }

    public void updateUserByUserId(String userId, String newUserName) {
        for (User user : users.values()) {
            if (user.getId().equals(userId)) {
                user.setName(newUserName);
                System.out.println("User with id " + userId + "'s name is updated");
                return;
            }
        }
        throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
    }
}
