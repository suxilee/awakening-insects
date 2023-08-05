package com.lansu.awakening.util;

import java.io.File;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

public class RSAKeyGenerator {

    private static final String PRIVATE_KEY_FILE = "private.key";
    private static final String PUBLIC_KEY_FILE = "public.key";

    public static void main(String[] args) {
        try {
            // Generate RSA key pair
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();


            // Save RSA private key to file
            FileOutputStream privateKeyFileOutputStream = new FileOutputStream(new File(PRIVATE_KEY_FILE));
            privateKeyFileOutputStream.write(Base64.getEncoder().encodeToString(privateKey.getEncoded()).getBytes());
            privateKeyFileOutputStream.flush();
            privateKeyFileOutputStream.close();

            // Save RSA public key to file
            FileOutputStream publicKeyFileOutputStream = new FileOutputStream(new File(PUBLIC_KEY_FILE));
            publicKeyFileOutputStream.write(Base64.getEncoder().encodeToString(publicKey.getEncoded()).getBytes());
            publicKeyFileOutputStream.flush();
            publicKeyFileOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}