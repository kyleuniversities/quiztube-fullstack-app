package com.ku.quizzical.common.helper.string;

import java.util.Map;
import com.ku.quizzical.common.helper.MapHelper;

/**
 * Helper class for Character Operations
 */
public class CharacterHelper {
    private static final Map<Character, Character> PARENTHESES_CHARACTER_MAP = CharacterHelper
            .makeParentheseCharacterMap();

    /**
     * Gets corresponding closing parentheses character to opening parentheses
     * character
     */
    public static char getClosingParenthesesCharacter(char openingParenthesesCharacter) {
        return CharacterHelper.PARENTHESES_CHARACTER_MAP.get(openingParenthesesCharacter);
    }

    /**
     * Checks if a character is alphabetical
     */
    public static boolean isAlphabetical(char ch) {
        return ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z';
    }

    /**
     * Checks if a character is alphanumeric
     */
    public static boolean isAlphanumeric(char ch) {
        return CharacterHelper.isAlphabetical(ch) || ch >= '0' && ch <= '9';
    }

    /**
     * Checks if a character is white space
     */
    public static boolean isWhiteSpace(char ch) {
        return ch == ' ' || ch == '\t';
    }

    /**
     * Makes a map mapping opening parentheses characters to closing parentheses
     * characters
     */
    private static Map<Character, Character> makeParentheseCharacterMap() {
        Map<Character, Character> parentheseCharacterMap = MapHelper.newLinkedHashMap();
        MapHelper.put(parentheseCharacterMap, '(', ')');
        MapHelper.put(parentheseCharacterMap, '[', ']');
        MapHelper.put(parentheseCharacterMap, '{', '}');
        MapHelper.put(parentheseCharacterMap, '<', '>');
        return parentheseCharacterMap;
    }
}
