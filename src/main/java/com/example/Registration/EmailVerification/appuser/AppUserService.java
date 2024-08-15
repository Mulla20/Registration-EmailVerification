package com.example.Registration.EmailVerification.appuser;

import com.example.Registration.EmailVerification.registration.token.ConfirmationToken;
import com.example.Registration.EmailVerification.registration.token.ConfirmationTokenRepository;
import com.example.Registration.EmailVerification.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@AllArgsConstructor //will take away the need for an inner constructor
public class AppUserService implements UserDetailsService {//how we find users when we log in, with Spring Security

    private final static String USER_NOT_FOUND_MSG = "user with email address %s not found";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException
                (String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUp(AppUser appUser) {//will return string with link to connect appUser to validate user for signup
        boolean userExists = appUserRepository.findByEmail(appUser.getEmail()).isPresent();

        if (userExists) {
            throw new IllegalStateException("email already exists");
        }

        bCryptPasswordEncoder.encode(appUser.getPassword());//security use with bcrypt to ensure secure password will be encoded
        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));

        appUserRepository.save(appUser);//will save appUser after acquirement

        //Token Section

        String token = UUID.randomUUID().toString();
        // TODO: SEND confirmation token //todo was done, was kept to show how todo works
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        //TODO: SEND EMAIL

        return token; //this will return a token to indicate that everything is working (i.e. with postman)
    }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }



}
