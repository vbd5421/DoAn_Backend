package com.doan.backend.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Random;

public class StringUtils {

    private StringUtils() {
    }

    private static final Random RD = new Random();
    private static final String A2Z = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String FONT_VN = "UTF-8";

    /** codau. */
    private static final char[] VIET_CHARS = { 'à', 'á', 'ả', 'ã', 'ạ', 'ă', 'ằ', 'ắ', 'ẳ', 'ẵ', 'ặ', 'â', 'ầ', 'ấ',
            'ẩ', 'ẫ', 'ậ', 'À', 'Á', 'Ả', 'Ã', 'Ạ', 'Ă', 'Ằ', 'Ắ', 'Ẳ', 'Ẵ', 'Ặ', 'Â', 'Ầ', 'Ấ', 'Ẩ', 'Ẫ', 'Ậ', 'è',
            'é', 'ẻ', 'ẽ', 'ẹ', 'ê', 'ề', 'ế', 'ể', 'ễ', 'ệ', 'È', 'É', 'Ẻ', 'Ẽ', 'Ẹ', 'Ê', 'Ề', 'Ế', 'Ể', 'Ễ', 'Ệ',
            'ì', 'í', 'ỉ', 'ĩ', 'ị', 'Ì', 'Í', 'Ỉ', 'Ĩ', 'Ị', 'ò', 'ó', 'ỏ', 'õ', 'ọ', 'ô', 'ồ', 'ố', 'ổ', 'ỗ', 'ộ',
            'ơ', 'ờ', 'ớ', 'ở', 'ỡ', 'ợ', 'Ò', 'Ó', 'Ỏ', 'Õ', 'Ọ', 'Ô', 'Ồ', 'Ố', 'Ổ', 'Ỗ', 'Ộ', 'Ơ', 'Ờ', 'Ớ', 'Ở',
            'Ỡ', 'Ợ', 'ù', 'ú', 'ủ', 'ũ', 'ụ', 'ư', 'ừ', 'ứ', 'ử', 'ữ', 'ự', 'Ù', 'Ú', 'Ủ', 'Ũ', 'Ụ', 'ỳ', 'ý', 'ỷ',
            'ỹ', 'ỵ', 'Ỳ', 'Ý', 'Ỷ', 'Ỹ', 'Ỵ', 'đ', 'Đ', 'Ư', 'Ừ', 'Ử', 'Ữ', 'Ứ', 'Ự' };

    /** khongdau. */
    private static final char[] NORMAL_CHARS = { 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a',
            'a', 'a', 'a', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'e',
            'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E',
            'i', 'i', 'i', 'i', 'i', 'I', 'I', 'I', 'I', 'I', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o',
            'o', 'o', 'o', 'o', 'o', 'o', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O',
            'O', 'O', 'u', 'u', 'u', 'u', 'u', 'u', 'u', 'u', 'u', 'u', 'u', 'U', 'U', 'U', 'U', 'U', 'y', 'y', 'y',
            'y', 'y', 'Y', 'Y', 'Y', 'Y', 'Y', 'd', 'D', 'U', 'U', 'U', 'U', 'U', 'U' };


    private static final char[] SPECIAL_CHARS = { ' ', '!', '"', '#', '$', '&', '(', ')', '*', '+', ',', '.', '/', '?','[',']'};

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static String toOracleSearchLikeSuffix(String searchText) {
        return toOracleSearchLike(searchText);
    }

    public static String toOracleSearchLike(String searchText) {
        String escapeChar = "/";
        String[] arrSpPat = { "/", "%", "_" };

        for (String str : arrSpPat) {
            if (!StringUtils.isNullOrEmpty(searchText)) {
                searchText = searchText.replaceAll(str, escapeChar + str);
            }
        }
        searchText = "%" + searchText + "%";
        return searchText;
    }

    public static String escapeSql(String str) {
        if (str == null) {
            return null;
        }
        return str.replace("'", "''");
    }

    public static String toEscapeSqlSearchLike(String searchText) {
        searchText = escapeSql(searchText);

        for (int i = 0; i < SPECIAL_CHARS.length; i++) {
            searchText = searchText.replace(SPECIAL_CHARS[i], '-');
        }
        return searchText;
    }

    public static boolean isNullOrEmpty(final String s) {
        return isNullOrEmpty(s, true);
    }

    public static boolean isNullOrEmpty(final String s, boolean trim) {
        if (s == null || s.isEmpty()) {
            return true;
        }

        return (trim && s.trim().isEmpty());
    }

    public static String getSearchableString(String input) {
        if (isNullOrEmpty(input)) {
            return "";
        }

        input = input.toLowerCase().trim();
        for (int i = 0; i < VIET_CHARS.length; i++) {
            input = input.replace(VIET_CHARS[i], NORMAL_CHARS[i]);
        }

        return toEscapeSqlSearchLike(input);
    }

    public static String decodeFromUrl(String str) {
        if (isNullOrEmpty(str))
            return null;

        try {
            return URLDecoder.decode(str, FONT_VN);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean checkEmailRegex(String email){
        String regex = "^[a-zA-Z0-9!#$%&'*+/=?^`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$";
        return email.matches(regex);
    }
}
