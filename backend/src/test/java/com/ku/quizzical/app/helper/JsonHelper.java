package com.ku.quizzical.app.helper;

import org.json.JSONException;
import org.json.JSONObject;
import com.ku.quizzical.common.helper.string.StringHelper;

/**
 * Helper class for JSON Operations
 */
public class JsonHelper {
    /**
     * Creates a new JSON Object
     */
    public static JSONObject newJsonObject() {
        return new JSONObject();
    }

    /**
     * Puts a value in a JSON object
     */
    public static void put(JSONObject object, String key, String value) {
        try {
            object.put(key, value);
        } catch (JSONException exception) {
            System.out.println("JSON Exception has occurred");
        }
    }

    /**
     * Converts a JSON Object to a String
     */
    public static String toString(JSONObject object) {
        return StringHelper.toString(object);
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private JsonHelper() {
        super();
    }
}
