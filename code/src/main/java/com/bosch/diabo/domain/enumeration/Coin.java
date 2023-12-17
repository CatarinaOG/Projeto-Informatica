package com.bosch.diabo.domain.enumeration;

/**
 * The Coin enumeration.
 */
public enum Coin {
    EUR,
    USD,
    AUD,
    GBP,
    XCD,
    XOF,
    NZD,
    XAF,
    ZAR,
    XPF,
    DKK,
    RUB,
    INR,
    JOD,
    MAD,
    BND,
    HKD,
    SGD,
    AMD,
    CHF,
    ANG,
    TRY,
    MRU,
    SHP,
    FKP,
    ILS,
    CAD,
    NOK,
    CLP,
    CNY;

    public static Coin fromString(String value) {
        switch (value) {
            case "EUR":
                return EUR;
            case "USD":
                return USD;
            case "GBP":
                return GBP;
            case "XCD":
                return XCD;
            case "XOF":
                return XOF;
            case "NZD":
                return NZD;
            case "XAF":
                return XAF;
            case "ZAR":
                return ZAR;
            case "XPF":
                return XPF;
            case "DKK":
                return DKK;
            case "RUB":
                return RUB;
            case "INR":
                return INR;
            case "JOD":
                return JOD;
            case "MAD":
                return MAD;
            case "BND":
                return BND;
            case "HKD":
                return HKD;
            case "SGD":
                return SGD;
            case "AMD":
                return AMD;
            case "CHF":
                return CHF;
            case "ANG":
                return ANG;
            case "TRY":
                return TRY;
            case "MRU":
                return MRU;
            case "SHP":
                return SHP;
            case "FKP":
                return FKP;
            case "ILS":
                return ILS;
            case "CAD":
                return CAD;
            case "NOK":
                return NOK;
            case "CLP":
                return CLP;
            case "CNY":
                return CNY;
            default:
                return EUR;
        }
    }
}
