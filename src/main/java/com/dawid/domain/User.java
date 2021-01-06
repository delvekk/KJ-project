package com.dawid.domain;



import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email(message = "Podaj prawidłowy email")
    @NotEmpty(message = "Podaj email")
    private String email;
    @Length(min = 5, message = "Hasło musi mieć conajmniej 5 znaków")
    @Transient
    private String password;
    @NotEmpty(message = "Podaj imię")
    private String name;
    @NotEmpty(message = "Podaj nazwisko")
    private String lastname;
    private String address;
    private Integer active;
    private String confirmationToken;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Review> comments = new HashSet<>();
    @OneToMany(mappedBy = "userInfo")
    private Set<Order> orders = new HashSet<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> setAuths = new HashSet<>();
        for (Role userRole : this.roles) {
            setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
        }
        return setAuths;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if(active > 0){
            return true;
        } else{
            return false;
        }
    }

}