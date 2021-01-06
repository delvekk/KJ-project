package com.dawid.converters;

import com.dawid.commands.RoleCommand;
import com.dawid.domain.Role;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RoleCommandToRole implements Converter<RoleCommand, Role> {


    @Nullable
    @Synchronized
    @Override
    public Role convert(RoleCommand source) {
        if(source == null){
            return null;
        }
        Role role = new Role();
        role.setId(source.getId());
        role.setRole(source.getRole());

        return role;
    }
}
