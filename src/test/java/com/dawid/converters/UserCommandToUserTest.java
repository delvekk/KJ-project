package com.dawid.converters;

import com.dawid.commands.RoleCommand;
import com.dawid.commands.UserCommand;
import com.dawid.domain.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserCommandToUserTest {
    public static final Long ID_VALUE = 1L;
    public static final String EMAIL = "email@email.com";
    public static final String PASSWORD = "password";
    public static final String ADDRESS = "address";
    public static final String TOKEN = "token";
    public static final String NAME = "name";
    public static final String LASTNAME = "lastname";
    public static final Integer ACTIVE = 1;
    public static final Long ROLE_ID = 1L;

    UserCommandToUser converter;





    @Before
    public void setUp() throws Exception {
        converter = new UserCommandToUser(new RoleCommandToRole());
    }

    @Test
    public void convert() {
        UserCommand command = new UserCommand();
        command.setId(ID_VALUE);
        command.setEmail(EMAIL);
        command.setPassword(PASSWORD);
        command.setName(NAME);
        command.setLastname(LASTNAME);
        command.setActive(ACTIVE);
        command.setAddress(ADDRESS);
        command.setConfirmationToken(TOKEN);

        RoleCommand roleCommand = new RoleCommand();
        roleCommand.setId(ROLE_ID);

        command.getRoles().add(roleCommand);

        User user = converter.convert(command);

        assertEquals(ID_VALUE, user.getId());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(PASSWORD, user.getPassword());
        assertEquals(NAME, user.getName());
        assertEquals(LASTNAME, user.getLastname());
        assertEquals(ACTIVE, user.getActive());
        assertEquals(ADDRESS, user.getAddress());
        assertEquals(TOKEN, user.getConfirmationToken());
        assertEquals(1, user.getRoles().size());
    }
}