package com.example.Registration.EmailVerification.registration;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

//Business logic to confirm email validation to update database
@Service
public class EmailValidation implements Predicate<String> {
    @Override
    public boolean test(String s) {
//        TODO: Regex to validate email
        return true;
    }
}
