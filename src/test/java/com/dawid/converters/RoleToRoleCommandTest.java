package com.dawid.converters;

import com.dawid.commands.RoleCommand;
import com.dawid.domain.Role;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoleToRoleCommandTest {

    public static final Long ID_VALUE = new Long(1L);
    public static final String ROLE = "ROLE";
    RoleToRoleCommand roleToRoleCommand;

    @Before
    public void setUp() throws Exception {
        roleToRoleCommand = new RoleToRoleCommand();
    }

    @Test
    public void convert() {
        Role role = new Role();
        role.setId(ID_VALUE);
        role.setRole(ROLE);


        RoleCommand command = roleToRoleCommand.convert(role);


        assertEquals(ID_VALUE, command.getId());
        assertEquals(ROLE, role.getRole());

    }
}