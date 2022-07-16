package com.example.onlineshop.utils;

import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;

public class InputValidator {
    public enum InputErrors {
        INVALID_NAME,
        INVALID_NUMBER,
        INVALID_PASSWORD,

        INVALID_RATING,
        INVALID_TITLE,
        INVALID_TEXT
    }

    //name
    public static void nameValidation(@Nullable String name, ObservableArrayList<InputErrors> errorsList) {
        if (name != null) {
            if (name.length() < 3)
                errorsList.add(InputErrors.INVALID_NAME);
        } else errorsList.add(InputErrors.INVALID_NAME);

    }


    //number TODO: More advanced validation should be implemented
    public static void phoneNumberValidation(@Nullable String number, ObservableArrayList<InputErrors> errorsList) {
        if (number != null) {
            String numberValue = number.trim();
            if (numberValue.startsWith("+98")) {
                numberValue = numberValue.substring(3);
            } else if (numberValue.startsWith("0")) {
                numberValue = numberValue.substring(1);
            }
            if (numberValue.length() != 10) {
                errorsList.add(InputErrors.INVALID_NUMBER);
            }
        } else {
            errorsList.add(InputErrors.INVALID_NUMBER);
        }
    }

    public static void passwordValidation(@Nullable String password, ObservableArrayList<InputErrors> errorsList) {
        if (password != null) {
            if (password.length() < 6)
                errorsList.add(InputErrors.INVALID_PASSWORD);
        } else errorsList.add(InputErrors.INVALID_PASSWORD);
    }

    public static void titleValidation(@Nullable String title, ObservableArrayList<InputErrors> errorsList) {

        //title validation
        if (title != null) {
            if (title.length() < 3 || title.length() >18)
                errorsList.add(InputErrors.INVALID_TITLE);
        } else errorsList.add(InputErrors.INVALID_TITLE);


    }

    public static void textValidation(@Nullable String text, ObservableArrayList<InputErrors> errorsList) {
        //title validation
        if (text != null) {
            if (text.length() < 3)
                errorsList.add(InputErrors.INVALID_TEXT);
        } else errorsList.add(InputErrors.INVALID_TEXT);
    }

    public static void ratingValidation(@Nullable Float rating, ObservableArrayList<InputErrors> errorsList) {

        //rating validation -- show snackBar if not valid.
        if (rating != null) {
            if (rating > 5 | rating < 1)
                errorsList.add(InputErrors.INVALID_RATING);
        } else errorsList.add(InputErrors.INVALID_RATING);

    }



}