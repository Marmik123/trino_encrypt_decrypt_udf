package com.crisil.trino.util;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class EncryptDecrypt {
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 128;
    private static final String ALGORITHM = "AES";

    //This key generation function is used if you want PBE (Password based encryption).
    public static SecretKey generateKey(char[] password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), ALGORITHM);
    }


    public static String encrypt(String plainText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedText = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedText);
    }

    public static String decrypt(String encryptedText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedText = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedText);
    }

    public static void main(String[] args) throws Exception {
        String password = "test@123";
        byte[] salt = SecureRandom.getInstanceStrong().generateSeed(16);


        SecretKey key = generateKey(password.toCharArray(), salt);
        String encrypted = encrypt("Hello, Trino!", key);
        String decrypted = decrypt(encrypted, key);

        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}
