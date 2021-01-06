package com.dawid.repositories;

import com.dawid.domain.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@Slf4j
public class RoleRepositoryIT {


    @Autowired
    RoleRepository roleRepository;


    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findByRole() throws Exception {
        Role role = roleRepository.findByRole("USER");

        assertEquals("USER", role.getRole());

    }
}