package mx.com.camacho.mdnotes.utils;

import mx.com.camacho.mdnotes.constants.StringConstants;

public final class StringUtils {

    public static String truncate (String toTruncate, int maxLength) {
        return truncate(toTruncate, maxLength, StringConstants.COMMON_TRUNCATE_FORMAT);
    }

    public static String truncate (String toTruncate, int maxLength, String format) {
        if (maxLength <= 0) {
            throw new IllegalArgumentException(String.format(StringConstants.INVALID_ARGUMENT,
                    StringConstants.MAX_LENGTH, maxLength));
        }
        if (toTruncate == null || toTruncate.isBlank()) {
            throw new IllegalArgumentException(String.format(StringConstants.INVALID_ARGUMENT,
                    StringConstants.TO_TRUNCATE, toTruncate));
        }
        return toTruncate.length() <= maxLength ? toTruncate :
                String.format(StringConstants.TRUNCATION_FORMAT, toTruncate.substring(0, maxLength), format);
    }

    private StringUtils () {}

}
