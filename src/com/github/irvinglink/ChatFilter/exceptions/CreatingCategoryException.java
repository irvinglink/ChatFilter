package com.github.irvinglink.ChatFilter.exceptions;

public class CreatingCategoryException extends Exception {

    public CreatingCategoryException(int category) {
        super("There was not possible to create category: " + category);
    }

}
