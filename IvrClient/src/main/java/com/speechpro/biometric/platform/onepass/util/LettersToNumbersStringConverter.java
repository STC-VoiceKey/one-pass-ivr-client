package com.speechpro.biometric.platform.onepass.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sadurtinova on 02.02.2017.
 */
public class LettersToNumbersStringConverter {

    private static final Map<String, Integer> converter;
    static {
        Map<String, Integer> lettersToNumbers = new HashMap<>();
        lettersToNumbers.put("ноль",     0);
        lettersToNumbers.put("один",     1);
        lettersToNumbers.put("два",      2);
        lettersToNumbers.put("три",      3);
        lettersToNumbers.put("четыре",   4);
        lettersToNumbers.put("пять",     5);
        lettersToNumbers.put("шесть",    6);
        lettersToNumbers.put("семь",     7);
        lettersToNumbers.put("восемь",   8);
        lettersToNumbers.put("девять",   9);
        converter = Collections.unmodifiableMap(lettersToNumbers);
    }

    public static String convertLettersToNumbers(String letters){
        String numbers = "";
        for(String word:letters.split(" ")){
            numbers += converter.get(word);
        }
        return numbers;
    }

}
