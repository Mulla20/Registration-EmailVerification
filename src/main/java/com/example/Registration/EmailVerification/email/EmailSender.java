package com.example.Registration.EmailVerification.email;

public interface EmailSender {
    void send(String to, String email);
}
