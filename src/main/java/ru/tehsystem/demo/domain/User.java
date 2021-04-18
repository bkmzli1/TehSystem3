package ru.tehsystem.demo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.userdetails.UserDetails;
import ru.tehsystem.demo.domain.enums.Level;

import javax.persistence.*;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @JsonView(Views.Id.class)
    private String id;
    @JsonView(Views.UserBasic.class)
    private String username;
    private String password;
    @JsonView(Views.UserAll.class)
    private String email;
    @JsonView(Views.UserBasic.class)
    private String firstName;
    @JsonView(Views.UserBasic.class)
    private String lastName;
    @JsonView(Views.UserBasic.class)
    private String middleName;
    @JsonView(Views.UserAll.class)
    private String telephone;
    @JsonView(Views.UserBasic.class)
    private String ps;
    @JsonView(Views.UserAll.class)
    private Img img;
    @JsonView(Views.UserAll.class)
    private Img imgFon;
    @JsonView(Views.UserAll.class)
    private boolean isAccountNonExpired;
    @JsonView(Views.UserAll.class)
    private boolean isAccountNonLocked;
    @JsonView(Views.UserAll.class)
    private boolean isEnabled;
    @JsonView(Views.UserAll.class)
    private boolean isCredentialsNonExpired;
    @JsonView(Views.UserBasic.class)
    private Set<Roles> authorities;
    @JsonView(Views.UserAll.class)
    private Set<Notifications> notifications;

    public User() {
    }


    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "users_roles",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    @Override
    public Set<Roles> getAuthorities() {
        return this.authorities;
    }


    public void setAuthorities(Set<Roles> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {

        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(unique = true)
    @Override
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public void addRole(Roles roles) {
        if (this.authorities == null) {
            this.authorities = new HashSet<>();
        }
        this.authorities.add(roles);
    }


    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "middle_name")
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @OneToOne(fetch = FetchType.EAGER)
    public Img getImg() {
        return img;
    }

    public void setImg(Img img) {
        this.img = img;
    }

    @OneToOne(fetch = FetchType.EAGER)
    public Img getImgFon() {
        return imgFon;
    }

    public void setImgFon(Img imgFon) {
        this.imgFon = imgFon;
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_notifications",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "notifications_id", referencedColumnName = "id"))

    public Set<Notifications> getNotifications() {
        return notifications;
    }

    public void setNotifications(Set<Notifications> notifications) {

        this.notifications = notifications;
    }
}
