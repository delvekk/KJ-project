package com.dawid.converters;


import com.dawid.commands.UserCommand;
import com.dawid.domain.User;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UserCommandToUser implements Converter<UserCommand, User> {

    private final RoleCommandToRole roleCommandToRole;

    public UserCommandToUser(RoleCommandToRole roleCommandToRole) {
        this.roleCommandToRole = roleCommandToRole;
    }


    @Nullable
    @Synchronized
    @Override
    public User convert(UserCommand source) {
        if (source == null) {
            return null;
        }
        User user = new User();
        user.setId(source.getId());
        user.setPassword(source.getPassword());
        user.setEmail(source.getEmail());
        user.setActive(source.getActive());
        user.setLastname(source.getLastname());
        user.setName(source.getName());
        user.setAddress(source.getAddress());
        user.setConfirmationToken(source.getConfirmationToken());

        source.getRoles()
                .forEach(roleCommand -> user.getRoles().add(roleCommandToRole.convert(roleCommand)));

        return user;
    }
}
