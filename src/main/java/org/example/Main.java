package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserValidator validator = new UserValidator();

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Date of Birth (YYYY-MM-DD): ");
        String dateOfBirth = scanner.nextLine();

        boolean isUsernameValid = validator.validateUsername(username);
        boolean isEmailValid = validator.validateEmail(email);
        boolean isPasswordValid = validator.validatePassword(password);
        boolean isDateOfBirthValid = validator.validateDateOfBirth(dateOfBirth);

        if (isUsernameValid && isEmailValid && isPasswordValid && isDateOfBirthValid) {
            String token = validator.generateJWT(username);
            System.out.println("JWT Token: " + token);
        } else {
            System.out.println("Validation failed. Fields with issues:");
            if (!isUsernameValid)
                System.out.println("Username: invalid (minimum 4 characters required)");
            scanner.close();
            if (!isEmailValid)
                System.out.println("Email: invalid (must be a valid email address)");
            scanner.close();
            if (!isPasswordValid)
                System.out.println("Password: invalid (must be a strong password)");
            scanner.close();
            if (!isDateOfBirthValid)
                System.out.println("Date of Birth: invalid (must be 16 years or older)");
            scanner.close();
        }

        System.out.print("Enter JWT Token for verification: ");
        String tokenToVerify = scanner.nextLine();

        if (validator.verifyJWT(tokenToVerify))
            System.out.println("Verification passed");
        else
            System.out.println("Verification failed");

        scanner.close();
    }
}