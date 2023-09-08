package com.abel.onerway.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Utility class for cryptographic operations.
 * <p>
 * Note: SHA256 is not an asymmetric function, but a cryptographic hash function used to ensure data integrity
 * during transmission. It takes an input of arbitrary length and produces a string of 256 bits (32 bytes) as output.
 * <p>
 */
@Slf4j
public class CryptoUtil {

    /**
     * Encrypts a given message using the SHA-256 algorithm.
     *
     * @param message the message to be encrypted
     * @param confusion     the encryption key (optional, default value is Abel's private key)
     * @return the encrypted message as a string, or null if an error occurs
     */
    public static String hash(String message, String confusion) {
        String encryptedMessage = null;
        final String algorithm = "SHA-256";
        // 默认值是Abel的私钥
        confusion = StringUtil.isEmpty(confusion) ? "59c5b49a58c74340b28ecc68004e815a" : confusion;

        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            String concatStr = message + confusion;
            md.update(concatStr.getBytes(StandardCharsets.UTF_8));
            encryptedMessage = StringUtil.byte2Hex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            log.error("Error: NoSuchAlgorithmException, Algorithm {} is not available", algorithm, e);
        } catch (Exception e) {
            log.error("Error while encrypting message: {}", e.getMessage());
        }
        return encryptedMessage;
    }

    /**
     * Verifies the integrity of a given message by comparing it with a provided sign.
     *
     * @param confusion the encryption key (optional, default value is Abel's private key)
     * @param message   the message to be verified
     * @param sign      the sign to compare the message with
     * @return true if the message's integrity is verified, false otherwise
     */
    public static boolean verifyIntegrity(String confusion, String message, String sign) {
        return hash(message, confusion).equals(sign);
    }

    /**
     * Encrypts a given message using the SHA-256 algorithm.
     *
     * @param message the message to be encrypted
     * @param key the encryption key. It should be public key. But in some cases, it can be private key.
     * @return the encrypted message as a string, or null if an error occurs
     */
    public static String encryptWithRSA(String key, String message) {
        final String rsa = "RSA";
        final String sha256WithRSA = "SHA256withRSA";

        try {
            byte[] encryptedBytes = Base64.decodeBase64(key);
            PKCS8EncodedKeySpec encodedKey = new PKCS8EncodedKeySpec(encryptedBytes);
            KeyFactory rsaKeyFactory = KeyFactory.getInstance(rsa);
            PrivateKey privateKey = rsaKeyFactory.generatePrivate(encodedKey);
            Signature signature = Signature.getInstance(sha256WithRSA);
            signature.initSign(privateKey);
            signature.update(message.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeBase64String(signature.sign());
        } catch (NoSuchAlgorithmException e) {
            log.error("Error: NoSuchAlgorithmException, Algorithm {} is not available", rsa, e);
        } catch (InvalidKeySpecException e) {
            log.error("Error: InvalidKeySpecException, KeySpec is invalid", e);
        } catch (InvalidKeyException e) {
            log.error("Error: InvalidKeyException, Key is invalid", e);
        } catch (SignatureException e) {
            log.error("Error: SignatureException, Signature is invalid", e);
        }
        return null;
    }

    /**
     * Verifies the integrity of a message using the RSA encryption algorithm.
     *
     * @param key the public key used for verification. It should be the same key used for encryption.
     * @param message the message to be verified.
     * @param sign the signature of the message.
     * @return true if the message's integrity is verified, false otherwise.
     */
    public static boolean verifyIntegrityWithRSA(String key, String message, String sign) {
        final String rsa = "RSA";
        final String sha256WithRSA = "SHA256withRSA";

        try {
            byte[] decodedRsaKey = Base64.decodeBase64(key);
            X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(decodedRsaKey);
            KeyFactory keyFactory = KeyFactory.getInstance(rsa);
            PublicKey publicKey = keyFactory.generatePublic(encodedKeySpec);
            Signature signature = Signature.getInstance(sha256WithRSA);
            signature.initVerify(publicKey);
            signature.update(message.getBytes(StandardCharsets.UTF_8));
            return signature.verify(Base64.decodeBase64(sign));
        } catch (NoSuchAlgorithmException e) {
            log.error("Error: NoSuchAlgorithmException, Algorithm {} is not available", rsa, e);
        } catch (InvalidKeySpecException e) {
            log.error("Error: InvalidKeySpecException, KeySpec is invalid", e);
        } catch (InvalidKeyException e) {
            log.error("Error: InvalidKeyException, Key is invalid", e);
        } catch (SignatureException e) {
            log.error("Error: SignatureException, Signature is invalid", e);
        }
        return false;
    }
}
