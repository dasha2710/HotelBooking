package com.hotel.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

/**
 * Created by Dasha on 04.05.2015.
 */

public class PasswordGenerator {
    public static String generate() {
        int count = 6 + new Random().nextInt(12);
        return RandomStringUtils.randomAlphanumeric(count);
    }
}
