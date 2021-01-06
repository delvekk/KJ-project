package com.dawid.services;

import com.dawid.commands.UserCommand;
import com.dawid.domain.User;

public interface UserService {

    User findUserByEmail(String email);
    UserCommand findUserCommandByEmail(String email);
    void saveUser(User user);
    void saveUserCommand(UserCommand command);
    void updateUser(UserCommand user);
    User findByConfirmationToken(String confirmationToke);
    void activateAccount(User user);

}
