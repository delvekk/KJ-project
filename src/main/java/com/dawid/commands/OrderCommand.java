package com.dawid.commands;


import com.dawid.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderCommand {

    private Long id;
    private Integer price = 0;
    private User userInfo;
    private Date date;
    @Length(min = 2, message = "Imię musi zawierać minimum 2 znaki")
    private String name;
    @Length(min = 2, message = "Nazwisko musi zawierać minimum 2 znaki")
    private String lastName;
    @Email(message = "Podaj prawidłowy email")
    private String email;
    @NotEmpty(message = "Podaj adres")
    private String address;
    private List<ProductCommand> products = new ArrayList<>();





}
