package com.dawid.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserCommand {

    private Long id;
    @Email(message = "Podaj prawidłowy email")
    @NotEmpty(message = "Podaj email")
    private String email;
    @Length(min = 5, message = "Hasło musi mieć conajmniej 5 znaków")
    @Transient
    private String password;
    @NotEmpty(message = "Podaj imię")
    @Length(min = 2, message = "Imię musi zawierać minimum 2 znaki")
    private String name;
    @NotEmpty(message = "Podaj nazwisko")
    @Length(min = 2, message = "Nazwisko musi zawierać minimum 2 znaki")
    private String lastname;
    @NotEmpty(message = "Podaj adres")
    private String address;
    private Integer active;
    private String confirmationToken;
    private Set<RoleCommand> roles = new HashSet<>();

}
