package com.abel.onerway.utils;

import java.util.Map;

public class StringUtil {

    /**
     * Checks if a given string is empty (i.e., null or has zero length).
     *
     * @param str the string to check for emptiness
     * @return true if the string is empty, false otherwise
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }


    /**
     * This method checks if a given string is not empty.
     *
     * @param str The string to check.
     * @return True if the given string is not empty, false otherwise.
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * Concatenates the non-empty values in a given map and returns the result as a string.
     *
     * @param contentMap the map containing the key-value pairs to concatenate
     * @return a string containing the concatenated non-empty values from the map
     */
    public static String concatValue(Map<String, Object> contentMap) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : contentMap.entrySet()) {
            if (StringUtil.isNotEmpty((String) entry.getKey()) && null != entry.getValue() && !"sign".equals(entry.getKey())) {
                sb.append(entry.getValue());
            }
        }
        return sb.toString();
    }

    /**
     * Converts a byte array to a hexadecimal string representation.
     *
     * @param bytes the byte array to be converted
     * @return the hexadecimal string representation of the byte array
     */
    public static String byte2Hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte value : bytes) {
            String hexValue = Integer.toHexString(value & 0xFF);
            if (hexValue.length() == 1) {
                sb.append("0");
            }
            sb.append(hexValue);
        }
        return sb.toString();
    }
}
