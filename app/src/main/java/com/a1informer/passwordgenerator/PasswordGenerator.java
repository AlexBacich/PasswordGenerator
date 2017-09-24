package com.a1informer.passwordgenerator;


import java.util.Random;

/**
 * Created by Babich on 21.09.2017.
 * Class for generating random passwords/
 */

class PasswordGenerator {

    private static final int LOWER_CASE_A = 97;
    private static final int UPPER_CASE_A = 65;
    private static final int NUMBER_0 = 48;

    private static final char[] SYMBOLS_LOWER_SET;
    private static final char[] SYMBOLS_UPPER_SET;
    private static final char[] SYMBOLS_NUMBER_SET;
    private static final char[] SYMBOLS_SPECIAL_SET;

    // default values for input characters set
    private static boolean includeLowerCase = true;
    private static boolean includeUpperCase = true;
    private static boolean includeNumbers = false;
    private static boolean includeSpecialCharacters = false;

    private static boolean[] includeSet = new boolean[]{includeLowerCase, includeUpperCase, includeNumbers, includeSpecialCharacters};


    static {
        // filling sets with chars
        SYMBOLS_LOWER_SET = new char[26];
        SYMBOLS_UPPER_SET = new char[26];
        for (int i = 0; i < 26; i++) {
            SYMBOLS_LOWER_SET[i] = (char) (i + LOWER_CASE_A);
            SYMBOLS_UPPER_SET[i] = (char) (i + UPPER_CASE_A);
        }
        SYMBOLS_SPECIAL_SET = new char[]{33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 58, 59, 60, 61, 62, 63, 64};
        SYMBOLS_NUMBER_SET = new char[10];
        for (int i = 0; i < 10; i++) {
            SYMBOLS_NUMBER_SET[i] = (char) (i + NUMBER_0);
        }
    }


//    String generatePassword(){
//        return generatePassword(length, includeLowerCase, includeUpperCase, includeNumbers, includeSpecialCharacters);
//    }

    /**
     * Generates passwords with selected length and symbols set
     *
     * @param length     - password length;
     * @param lowerCase  - include lower cases in password (ON by default);
     * @param upperCase- include upper cases in password (ON by default);
     * @param numbers    - include numbers in password (OFF by default);
     * @param specialCh  - include special characters in password (OFF by default);
     * @return generated password. Returns <b>null</b> all parameters are set to false.
     */

    static String generatePassword(int length, boolean lowerCase, boolean upperCase, boolean numbers, boolean specialCh) {
        includeSet[0] = lowerCase;
        includeSet[1] = upperCase;
        includeSet[2] = numbers;
        includeSet[3] = specialCh;

        Random random = new Random();
        char[] password = new char[length];
        for (int i = 0; i < length; i++) {

            // check if there is at least one type for password characters selected
            boolean hasAtLeastOneCharacter = false;
            for (boolean b : includeSet) {
                if (b) hasAtLeastOneCharacter = true;
            }

            // Pick type for character
            int randomCharacterType;
            if (hasAtLeastOneCharacter) {
                do {
                    randomCharacterType = random.nextInt(includeSet.length);
                } while (!includeSet[randomCharacterType]);
            } else return null;

            // Generate random character for selected type
            switch (randomCharacterType) {
                case 0:
                    password[i] = SYMBOLS_LOWER_SET[(random.nextInt(SYMBOLS_LOWER_SET.length))];
                    break;
                case 1:
                    password[i] = SYMBOLS_UPPER_SET[(random.nextInt(SYMBOLS_UPPER_SET.length))];
                    break;
                case 2:
                    password[i] = SYMBOLS_NUMBER_SET[(random.nextInt(SYMBOLS_NUMBER_SET.length))];
                    break;
                case 3:
                    password[i] = SYMBOLS_SPECIAL_SET[(random.nextInt(SYMBOLS_SPECIAL_SET.length))];
                    break;

            }
        }

        return String.valueOf(password);
    }


    public static void main(String[] args) {

        //Generate set of values
        System.out.println("\n==Lower cases==");
        for (char c : SYMBOLS_LOWER_SET) {
            System.out.print(c);
        }

        System.out.println("\n==Upper cases==");
        for (char c : SYMBOLS_UPPER_SET) {
            System.out.print(c);
        }

        System.out.println("\n==Numbers==");
        for (char c : SYMBOLS_NUMBER_SET) {
            System.out.print(c);
        }

        System.out.println("\n==Special characters==");
        for (char c : SYMBOLS_SPECIAL_SET) {
            System.out.print(c);
        }

        //Generate set of random values
        Random random = new Random();
        char symbolLowerCase;
        char symbolUpperCase;
        char symbolSpecialCharacter;

        System.out.println("\n==Lower cases==");
        for (int i = 0; i < 20; i++) {
            symbolLowerCase = SYMBOLS_LOWER_SET[(random.nextInt(SYMBOLS_LOWER_SET.length))];
            System.out.print(symbolLowerCase);
        }

        System.out.println("\n==Upper cases==");
        for (int i = 0; i < 20; i++) {
            symbolUpperCase = SYMBOLS_UPPER_SET[(random.nextInt(SYMBOLS_UPPER_SET.length))];
            System.out.print(symbolUpperCase);

        }

        System.out.println("\n==Numbers==");
        for (int i = 0; i < 20; i++) {
            symbolSpecialCharacter = SYMBOLS_NUMBER_SET[(random.nextInt(SYMBOLS_NUMBER_SET.length))];
            System.out.print(symbolSpecialCharacter);
        }

        System.out.println("\n==Special characters==");
        for (int i = 0; i < 20; i++) {
            symbolSpecialCharacter = SYMBOLS_SPECIAL_SET[(random.nextInt(SYMBOLS_SPECIAL_SET.length))];
            System.out.print(symbolSpecialCharacter);
        }


    }

}
