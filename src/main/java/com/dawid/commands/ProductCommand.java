package com.dawid.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ProductCommand {

    private Long id;
    @Length(min = 2, message = "Nazwa produktu zbyt krótka")
    private String name;
    @NotNull(message = "Podaj cenę")
    private Integer price;
    @NotNull(message = "Podaj ilość")
    private Integer amount;
    private Boolean discount;
    private Boolean recent;
    private String description;
    private String image_url;
    private Set<CategoryCommand> categories = new HashSet<>();
    private Set<ReviewCommand> comments = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCommand command = (ProductCommand) o;
        return Objects.equals(id, command.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
