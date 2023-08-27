package com.stadnikov.voting.util;

import com.stadnikov.voting.model.Role;
import com.stadnikov.voting.model.User;
import com.stadnikov.voting.to.UserTo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UsersUtil {
    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getName(), userTo.getEmail().toLowerCase(), userTo.getPassword(), Role.USER);
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }
}