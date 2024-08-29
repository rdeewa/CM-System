package com.cms.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashingUtil {

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verifyPassword(String inputPassword, String storedHashedPassword) {
        // Hash the input password and compare it to the stored hashed password
        return hashPassword(inputPassword).equals(storedHashedPassword);
    }
}
