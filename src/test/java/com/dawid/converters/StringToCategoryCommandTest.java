package com.dawid.converters;

import com.dawid.commands.CategoryCommand;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringToCategoryCommandTest {

    public static final String ID_VALUE = "1";
    StringToCategoryCommand converter;


    @Before
    public void setUp() throws Exception {
        converter = new StringToCategoryCommand();
    }

    @Test
    public void convert() {
        CategoryCommand categoryCommand = converter.convert(ID_VALUE);

        assertEquals(ID_VALUE, categoryCommand.getId().toString());

    }
}