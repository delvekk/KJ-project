package com.dawid.converters;

import com.dawid.commands.UserCommand;
import com.dawid.domain.User;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UserToUserCommand implements Converter<User, UserCommand> {

    private final RoleToRoleCommand roleToRoleCommand;


    public UserToUserCommand(RoleToRoleCommand roleToRoleCommand) {
        this.roleToRoleCommand = roleToRoleCommand;
    }


    @Nullable
    @Synchronized
    @Override
    public UserCommand convert(User source) {
        if(source == null){
            return null;
        }

        UserCommand command = new UserCommand();
        command.setId(source.getId());
        command.setActive(source.getActive());
        command.setEmail(source.getEmail());
        command.setLastname(source.getLastname());
        command.setName(source.getName());
        command.setPassword(source.getPassword());
        command.setAddress(source.getAddress());
        command.setConfirmationToken(source.getConfirmationToken());

        source.getRoles()
                .forEach(role -> command.getRoles().add(roleToRoleCommand.convert(role)));

        return command;
    }
}
