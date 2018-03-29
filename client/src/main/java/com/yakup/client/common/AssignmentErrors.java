package com.yakup.client.common;

public enum AssignmentErrors {
    NUMBER_ALREADY_EXISTS(1, "Number already saved"),
    NUMBER_NOT_FOUND(2, "Number not found"),
    MAXIMUM_NUMBER_COULD_NOT_FOUND(3, "Maximum number could not be found"),
    NO_NUMBER_EXISTS(4, "No number exists"),
    UNEXPECTED_EXCEPTION(99, "Unexpected error occurred");


    private int errorCode;
    private String errorMessage;

    AssignmentErrors(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public static void main(String[] args) {
        System.out.println(AssignmentErrors.NUMBER_ALREADY_EXISTS.toString());
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return "{" +
                "\"errorCode\" : " + errorCode + ", " +
                "\"errorMessage\" : \"" + errorMessage + "\"" +
                "}";
    }
}
