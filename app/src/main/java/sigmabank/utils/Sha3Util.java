package sigmabank.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Sha3Util {
    private static final String algorithm = "SHA3-256"; 
    
    /**
     * Hashes the input using the SHA-3 256 algorithm.
     * 
     * @param input The input to be hashed
     * @return The hashed input
     */
    public static String sha3(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
            byte[] hash = digest.digest(bytes);
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Converts a byte array to a hexadecimal string.
     * 
     * @param bytes
     * @return
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hexString.append("0");
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}