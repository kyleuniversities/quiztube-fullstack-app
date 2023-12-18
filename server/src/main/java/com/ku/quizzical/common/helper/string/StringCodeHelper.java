package com.ku.quizzical.common.helper.string;

import com.ku.quizzical.common.util.string.StringMap;

/**
 * Helper class for String Code Operations
 */
public final class StringCodeHelper {
    // Class Fields
    private static final StringMap TO_CODE_REPLACEMENT_MAP =
            StringCodeHelper.makeToCodeReplacementMap();
    private static final StringMap TO_TEXT_REPLACEMENT_MAP =
            StringCodeHelper.makeToTextReplacementMap();

    /**
     * Creates a new String Builder
     */
    public static StringBuilder newBuilder() {
        return new StringBuilder();
    }

    /**
     * Converts text to its String literal representation
     */
    public static String toCode(String text) {
        return "\""
                + StringReplacementHelper.replace(text, StringCodeHelper.TO_CODE_REPLACEMENT_MAP)
                + "\"";
    }

    /**
     * Converts a String literal representation to its text
     */
    public static String toText(String code) {
        String unquotedCode = code.substring(1, code.length() - 1);
        return StringReplacementHelper.replace(unquotedCode,
                StringCodeHelper.TO_TEXT_REPLACEMENT_MAP);
    }

    /**
     * Creates the conversion map of noncode characters to their escape sequences
     */
    private static StringMap makeToCodeReplacementMap() {
        StringMap toCodeReplacementMap = StringMap.newInstance();
        toCodeReplacementMap.put("\\", "\\\\");
        toCodeReplacementMap.put("\n", "\\n");
        toCodeReplacementMap.put("\t", "\\t");
        toCodeReplacementMap.put("\"", "\\\"");
        return toCodeReplacementMap;
    }

    /**
     * Creates the conversion map of escape characters to their noncode characters
     */
    private static StringMap makeToTextReplacementMap() {
        StringMap toTextReplacementMap = StringMap.newInstance();
        toTextReplacementMap.put("\\\\", "\\");
        toTextReplacementMap.put("\\n", "\n");
        toTextReplacementMap.put("\\t", "\t");
        toTextReplacementMap.put("\\\"", "\"");
        return toTextReplacementMap;
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private StringCodeHelper() {
        super();
    }
}
