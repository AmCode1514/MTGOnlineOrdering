package com.CS320.app.server;
import java.util.Random;
import java.nio.charset.StandardCharsets;

public class TokenGenerator {
    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);
    private TokenGenerator() {

    }
    public static String generateRandomHexStringOfLength(int length) {
        Random rand = new Random();
        byte[] bytes = new byte[16];
        rand.nextBytes(bytes);
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8);
    }
}
