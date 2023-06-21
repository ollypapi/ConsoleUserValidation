package org.example;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class UserValidator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );
    public boolean validateUsername(String username) {
        return username != null && username.length() >= 4;
    }

    public boolean validateEmail(String email) {
        return email != null && !email.isEmpty() && EMAIL_PATTERN.matcher(email).matches();
    }

    public boolean validatePassword(String password) {
        return password != null && !password.isEmpty() &&
                password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$");
    }
    public boolean validateDateOfBirth(String dateOfBirth) {
        if (dateOfBirth == null || dateOfBirth.isEmpty())
            return false;

        LocalDate dob = LocalDate.parse(dateOfBirth, DateTimeFormatter.ISO_DATE);
        LocalDate today = LocalDate.now();
        LocalDate minimumAge = today.minusYears(16);

        return dob.isBefore(minimumAge);
    }

    public String generateJWT(String username) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        return JWT.create()
                .withClaim("username", username)
                .sign(algorithm);
    }
    public boolean verifyJWT(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            DecodedJWT jwt = JWT.require(algorithm).build().verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
