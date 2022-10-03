package com.sergdalm.entity;

public record MobilePhoneNumber(String zoneCode,
                                String firstPart,
                                String secondPart,
                                String thirdPart) {

    private static final String PREFIX = "+7";
    private static final String OPEN_PARENTHESIS = "(";
    private static final String CLOSED_PARENTHESIS = ")";
    private static final String HYPHEN = "-";

    public String getMobilePhoneNumber() {
        return PREFIX + OPEN_PARENTHESIS + zoneCode + CLOSED_PARENTHESIS +
                firstPart + HYPHEN + secondPart + HYPHEN + thirdPart;
    }

    @Override
    public String toString() {
        return getMobilePhoneNumber();
    }
}
