package com.entelgy.mediapro.spaininaday.util;

import android.widget.EditText;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService {

    public final static String PATRON_CONTRASENA = "$$$$$$$$";
    public final static String DATE_DELIMITER_1 = ".";
    public final static String DATE_DELIMITER_2 = "-";
    public final static String DATE_DELIMITER_3 = "/";

    public static Pattern emailPattern = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    public static Pattern phonePattern = Pattern.compile("\\d+");
    public static Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{7,}$");

    public static boolean isValidName(String value) {
        return value.length() > 0;
    }

    public static boolean isValidEmail(String value) {
        Matcher matcher = emailPattern.matcher(value);
        return matcher.find();
    }

    public static boolean isValidPhone(String value) {
        Matcher matcher = phonePattern.matcher(value);
        return matcher.find();
    }

    public static boolean isValidPassword(String value) {
        Matcher matcher = passwordPattern.matcher(value);
        return matcher.find();
    }

    public static boolean isValidDateOfBirth(String value) {
        boolean result = false;
        StringTokenizer tokenizer = new StringTokenizer(value, DATE_DELIMITER_1);
        if (tokenizer.countTokens() == 3) {
            return isValidDate(tokenizer);
        } else {
            tokenizer = new StringTokenizer(value, DATE_DELIMITER_2);
            if (tokenizer.countTokens() == 3) {
                return isValidDate(tokenizer);
            } else {
                tokenizer = new StringTokenizer(value, DATE_DELIMITER_3);
                if (tokenizer.countTokens() == 3) {
                    return isValidDate(tokenizer);
                }
            }
        }

        return result;
    }

    private static boolean isValidDate(StringTokenizer tokenizer) {
        boolean result = false;

        try {
            int day = Integer.parseInt(tokenizer.nextToken());
            int month = Integer.parseInt(tokenizer.nextToken());
            int year = Integer.parseInt(tokenizer.nextToken());

            result = isValidDay(day) && isValidMonth(month) && isValidYear(year);

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return result;
    }

    private static boolean isValidDay(int value) {
        return (value >= 1) && (value <= 31);
    }

    private static boolean isValidMonth(int value) {
        return (value >= 1) && (value <= 12);
    }

    private static boolean isValidYear(int value) {
        return (value >= 1900) && (value <= 2000);
    }

    public static String parseDateFromYYYYMMDD(String value) {
        String result = "";
        StringTokenizer tokenizer = new StringTokenizer(value, DATE_DELIMITER_2);
        String year = tokenizer.nextToken();
        String month = tokenizer.nextToken();
        String day = tokenizer.nextToken();

        if (day.indexOf("T") != -1) {
            day = day.substring(0, day.indexOf("T"));
        }

        result = day + "/" + month + "/" + year;
        return result;
    }

    public static String parseDate(String value) {
        String result = "1900-31-01";

        StringTokenizer tokenizer = new StringTokenizer(value, DATE_DELIMITER_1);
        if (tokenizer.countTokens() == 3) {
            result = buildDate(tokenizer);
        } else {
            tokenizer = new StringTokenizer(value, DATE_DELIMITER_2);
            if (tokenizer.countTokens() == 3) {
                result = buildDate(tokenizer);
            } else {
                tokenizer = new StringTokenizer(value, DATE_DELIMITER_3);
                if (tokenizer.countTokens() == 3) {
                    result = buildDate(tokenizer);
                }
            }
        }

        return result;
    }

    private static String buildDate(StringTokenizer tokenizer) {
        String day = tokenizer.nextToken();
        String month = tokenizer.nextToken();
        String year = tokenizer.nextToken();

        return year + "-" + month + "-" + day;
    }

    public static String getString(EditText editText) {
        return editText.getText().toString().trim();
    }


}
