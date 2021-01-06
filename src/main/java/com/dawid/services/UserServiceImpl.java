package com.dawid.services;

import com.dawid.commands.UserCommand;
import com.dawid.converters.UserCommandToUser;
import com.dawid.converters.UserToUserCommand;
import com.dawid.domain.Role;
import com.dawid.domain.User;
import com.dawid.repositories.RoleRepository;
import com.dawid.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserCommandToUser userCommandToUser;
    private final UserToUserCommand userToUserCommand;


    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, UserCommandToUser userCommandToUser, UserToUserCommand userToUserCommand) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userCommandToUser = userCommandToUser;
        this.userToUserCommand = userToUserCommand;
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);

        return user;
    }

    @Override
    public UserCommand findUserCommandByEmail(String email) {
        return userToUserCommand.convert(findUserByEmail(email));
    }

    @Override
    public void saveUser(User user) {
        log.debug(user.getPassword());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    @Override
    public void saveUserCommand(UserCommand command) {
        User user = userCommandToUser.convert(command);
        user.setActive(0);
        saveUser(user);
    }

    @Override
    public void updateUser(UserCommand userCommand) {
        User user = userCommandToUser.convert(userCommand);
        saveUser(user);
    }

    public void activateAccount(User user){
        user.setActive(1);
        userRepository.save(user);
    }

    @Override
    public User findByConfirmationToken(String confirmationToke) {

        return userRepository.findByConfirmationToken(confirmationToke);
    }


}
