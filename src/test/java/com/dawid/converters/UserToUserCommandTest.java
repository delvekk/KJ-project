package com.dawid.converters;

import com.dawid.commands.UserCommand;
import com.dawid.domain.Role;
import com.dawid.domain.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserToUserCommandTest {

    public static final Long ID_VALUE = 1L;
    public static final String EMAIL = "email@email.com";
    public static final String PASSWORD = "password";
    public static final String ADDRESS = "address";
    public static final String TOKEN = "token";
    public static final String NAME = "name";
    public static final String LASTNAME = "lastname";
    public static final Integer ACTIVE = 1;
    public static final Long ROLE_ID = 1L;

    UserToUserCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new UserToUserCommand(new RoleToRoleCommand());
    }

    @Test
    public void convert() {
        User user = new User();
        user.setId(ID_VALUE);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        user.setName(NAME);
        user.setLastname(LASTNAME);
        user.setActive(ACTIVE);
        user.setAddress(ADDRESS);
        user.setConfirmationToken(TOKEN);

        Role role = new Role();
        role.setId(ROLE_ID);

        user.getRoles().add(role);

        UserCommand userCommand = converter.convert(user);

        assertEquals(ID_VALUE, userCommand.getId());
        assertEquals(EMAIL, userCommand.getEmail());
        assertEquals(PASSWORD, userCommand.getPassword());
        assertEquals(NAME, userCommand.getName());
        assertEquals(LASTNAME, userCommand.getLastname());
        assertEquals(ACTIVE, userCommand.getActive());
        assertEquals(ADDRESS, userCommand.getAddress());
        assertEquals(TOKEN, userCommand.getConfirmationToken());
        assertEquals(1, userCommand.getRoles().size());
    }
}