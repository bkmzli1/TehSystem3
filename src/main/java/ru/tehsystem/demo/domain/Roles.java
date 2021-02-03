package ru.tehsystem.demo.domain;


import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import ru.tehsystem.demo.domain.enums.Role;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Roles implements GrantedAuthority {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;
    @Column(unique = true)
    private Role authority;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return authority.getRole();
    }

    public void setAuthority(Role authority) {
        this.authority = authority;
    }
}
