package com.dawid.converters;

import com.dawid.commands.RoleCommand;
import com.dawid.domain.Role;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoleCommandToRoleTest {

    public static final Long ID_VALUE = new Long(1L);
    public static final String ROLE = "ROLE";
    RoleCommandToRole converter;


    @Before
    public void setUp() throws Exception {
        converter = new RoleCommandToRole();
    }

    @Test
    public void convert() throws Exception{
        RoleCommand command = new RoleCommand();
        command.setId(ID_VALUE);
        command.setRole(ROLE);

        Role role = converter.convert(command);

        assertEquals(ID_VALUE, role.getId());
        assertEquals(ROLE, role.getRole());

    }
}