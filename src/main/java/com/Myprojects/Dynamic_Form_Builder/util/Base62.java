package com.Myprojects.Dynamic_Form_Builder.util;

import java.util.Random;

public class Base62 {
    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final Random RANDOM = new Random();

    public static String encode(long value) {
        StringBuilder encoded = new StringBuilder();
        while (value > 0) {
            encoded.append(BASE62.charAt((int) (value % 62)));
            value /= 62;
        }
        return encoded.reverse().toString();
    }

    public static String generateUniqueId() {
        long timestamp = System.currentTimeMillis();
        long randomSuffix = RANDOM.nextInt(1_000_000); // Add randomness
        return encode(timestamp + randomSuffix);
    }
}
