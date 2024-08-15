package com.example.Registration.EmailVerification.appuser;

import com.example.Registration.EmailVerification.registration.token.ConfirmationToken;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

//Lombok uses
@Getter //Used Lombok to acquire getters and setters and other methods,
@Setter //and getting defined by annotations.
@EqualsAndHashCode
@NoArgsConstructor
@Entity //this will be a table in our Database

public class AppUser implements UserDetails {

    //Dynamic attributes

    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @Id //Acquired from persistence library
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    //attributes
    private Long id;
 // @Getter//these getters bypass the need to physically get the method and let lombok take care of the process
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)//this helps display the Enums class
    private AppUserRole appUserRole;
    private Boolean locked = false; //check if account is locked
    private Boolean enabled = false;

    //Constructor
    public AppUser(String firstname, String lastname, String email, String password, AppUserRole appUserRole) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.appUserRole = appUserRole;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return email;
    }

    public String getFirstName() {
        return firstname;
    }

    public String getLastName() {
        return lastname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
       // return UserDetails.super.isEnabled();
        return enabled;
    }


//Test code
//    @Setter
//    @Getter
//    @OneToMany(mappedBy = "appUser")
//    private Collection<ConfirmationToken> confirmationToken;

}
