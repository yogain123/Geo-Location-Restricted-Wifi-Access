package com.pickachu.momma.utils;

import android.content.Context;

import com.pickachu.momma.fragments.BaseFragment;

import java.util.regex.Pattern;

/**
 * Created by vaibhavsinghal on 3/11/15.
 */
public class StringUtils {

    public static String getTextFromStringResource(Context context, int stringResourceId) {
        if (context != null) {
            return context.getResources().getString(stringResourceId);
        }
        return "";
    }

    public static String getTextFromStringResource(BaseFragment baseFragment, int stringResourceId) {
        if (baseFragment != null) {
            return baseFragment.getResources().getString(stringResourceId);
        }
        return "";
    }

    public static String extractValidString(String text) {
        for (int counter = 0; counter < text.length(); counter++) {
            if (text.length() > 0) {
                int indexOfFirstSpace = text.indexOf(" ");
                if (indexOfFirstSpace == -1 || indexOfFirstSpace > 0) {
                    break;
                } else if (indexOfFirstSpace == 0 && text.split(" ").length > 0 && text.split(" ")[1] != null) {
                    //text = text.replaceFirst(" ", "");
                    text = text.replaceFirst(Pattern.quote("  "), "");
                } else {
                    text = "";
                    break;
                }
            }
        }

        if (!isValidString(text)) {
            text = "";
        }

        return text;
    }

    public static boolean isValidString(String testString) {
        return testString != null && !("").equals(testString) && !("null").equals(testString);

    }

    public static boolean isValidNumberString(String testString) {
        String regexForDigit = "\\d+";

        return isValidString(testString) && testString.matches(regexForDigit);

    }

    public static boolean isValidMobileString(String testString) {
        String regexForDigit = "\\d+";

        return isValidNumberString(testString) && testString.length() == 10;

    }
}
