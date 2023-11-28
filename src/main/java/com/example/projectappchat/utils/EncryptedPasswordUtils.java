package com.example.projectappchat.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptedPasswordUtils {

    public static String encryptedPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public static void main(String[] args) {
        String password = "123";
        String encryptedPassword = encryptedPassword(password);

        System.out.println("Encrypted Password: " + encryptedPassword);

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        boolean isPasswordMatches = bcrypt.matches(
                "123",
                "$2a$10$VH0B9FHS40gMcUgdXrs6mO.lI7VjbJbwtWgO08aS4yH49k0PVdwL."
        );


        if (isPasswordMatches) { // correct password
            System.out.println("true");
        } else { // Wrong Password
            System.out.println("false");
        }
    }
}
