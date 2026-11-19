package com.womensafety.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/** Salted PBKDF2 password hashing. Stored format: iterations:salt:hash (Base64). */
public class PasswordUtil {
    private static final int ITERATIONS = 65536;
    private static final int KEY_BITS = 256;

    public static String hash(String password) {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        byte[] h = pbkdf2(password.toCharArray(), salt, ITERATIONS);
        return ITERATIONS + ":" + Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(h);
    }

    public static boolean verify(String password, String stored) {
        try {
            String[] p = stored.split(":");
            int it = Integer.parseInt(p[0]);
            byte[] salt = Base64.getDecoder().decode(p[1]);
            byte[] expected = Base64.getDecoder().decode(p[2]);
            byte[] actual = pbkdf2(password.toCharArray(), salt, it);
            return java.security.MessageDigest.isEqual(expected, actual);   // constant-time compare
        } catch (Exception e) {
            return false;
        }
    }

    private static byte[] pbkdf2(char[] pw, byte[] salt, int it) {
        try {
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                    .generateSecret(new PBEKeySpec(pw, salt, it, KEY_BITS)).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException(e);
        }
    }
}
