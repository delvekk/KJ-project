package com.dawid.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
public class ReviewCommand {

    private Long id;
    @Length(min = 2, message = "Komentarz zbyt krótki")
    private String review;
    private Long productId;
    private UserCommand user;
    private Date date;





}
