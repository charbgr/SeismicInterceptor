package com.charbgr.seismicinterceptor.utils;

public class TextUtils {
    private TextUtils() {
        //no instances
    }

    /**
     * Returns a string containing the tokens joined by delimiters,
     * taking in considerations if the token of the iterable is null
     *
     * @param tokens an array objects to be joined. Strings will be formed from
     *               the objects by calling object.toString().
     */
    public static String join(CharSequence delimiter, Iterable tokens) {
        StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        for (Object token : tokens) {

            if (token == null || isEmpty(token.toString())) {
                continue;
            }

            if (firstTime) {
                firstTime = false;
            } else {
                sb.append(delimiter);
            }
            sb.append(token.toString());
        }

        //all the tokens where null
        if (firstTime) {
            return null;
        }

        return sb.toString();
    }

    /**
     * Check for a given String if it is Empty which means it is either null or has zero length,
     * when trimmed.
     *
     * @param string
     */
    public static boolean isEmpty(String string) {
        return string == null || string.trim().length() == 0;
    }
}
