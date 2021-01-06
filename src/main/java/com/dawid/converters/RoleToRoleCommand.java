package com.dawid.converters;

import com.dawid.commands.RoleCommand;
import com.dawid.domain.Role;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RoleToRoleCommand implements Converter<Role, RoleCommand> {

    @Nullable
    @Synchronized
    @Override
    public RoleCommand convert(Role source) {
        if(source == null){
            return null;
        }
        RoleCommand command = new RoleCommand();
        command.setId(source.getId());
        command.setRole(source.getRole());

        return command;
    }
}
