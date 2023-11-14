package com.bosch.diabo.domain.enumeration;

/**
 * The ABCClassification enumeration.
 */
public enum ABCClassification {
    A,
    B,
    C;

    public static ABCClassification fromString(String value) {
        switch (value) {
            case "A":
                return A;
            case "B":
                return B;
            case "C":
                return C;
            default:
                return A;
        }
    }
}