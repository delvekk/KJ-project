package com.dawid.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class CategoryCommand {

    private Long id;
    @Length(min = 2, message = "Kategoria musi mieć minimum dwa znaki")
    private String description;
}
