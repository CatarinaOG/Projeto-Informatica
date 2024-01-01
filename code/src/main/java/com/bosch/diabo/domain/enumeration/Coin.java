package com.bosch.diabo.domain.enumeration;

/**
 * The Coin enumeration.
 */
public enum Coin {
    EUR,
    USD,
    JPY,
    GBP,
    CNY,
    AUD,
    CAD,
    CHF,
    HKD,
    SGD,
    SEK,
    KRW,
    NOK,
    NZD,
    INR,
    MXN,
    TWD,
    ZAR,
    BRL,
    DKK,
    PLN,
    THB,
    ILS,
    IDR,
    CZK,
    AED,
    TRY,
    HUF,
    CLP,
    SAR,
    PHP,
    MYR,
    COP,
    RUB,
    RON,
    PEN,
    BHD,
    BGN;

    public static Coin fromString(String value) {
        switch (value) {
            case "EUR":
                return EUR;
            case "USD":
                return USD;
            case "GBP":
                return GBP;
            case "JPY":
                return JPY;
            case "CNY":
                return CNY;
            case "AUD":
                return AUD;
            case "CAD":
                return CAD;
            case "CHF":
                return CHF;
            case "HKD":
                return HKD;
            case "SGD":
                return SGD;
            case "SEK":
                return SEK;
            case "KRW":
                return KRW;
            case "NOK":
                return NOK;
            case "NZD":
                return NZD;
            case "INR":
                return INR;
            case "MXN":
                return MXN;
            case "TWD":
                return TWD;
            case "ZAR":
                return ZAR;
            case "BRL":
                return BRL;
            case "DKK":
                return DKK;
            case "PLN":
                return PLN;
            case "THB":
                return THB;
            case "ILS":
                return ILS;
            case "IDR":
                return IDR;
            case "CZK":
                return CZK;
            case "AED":
                return AED;
            case "TRY":
                return TRY;
            case "HUF":
                return HUF;
            case "CLP":
                return CLP;
            case "SAR":
                return SAR;
            case "PHP":
                return PHP;
            case "MYR":
                return MYR;
            case "COP":
                return COP;
            case "RUB":
                return RUB;
            case "RON":
                return RON;
            case "PEN":
                return PEN;
            case "BHD":
                return BHD;
            case "BGN":
                return BGN;
            default:
                return EUR;
        }
    }
}
