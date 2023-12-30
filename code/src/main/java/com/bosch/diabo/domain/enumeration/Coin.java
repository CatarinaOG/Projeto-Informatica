package com.bosch.diabo.domain.enumeration;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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

    public String getStyle(){
        switch(this){
            case EUR:
                return "€";
            case USD:
                return "$";
            case GBP:
                return "£";
            case JPY:
                return "¥";
            case CNY:
                return "¥";
            case AUD:
                return "A$";
            case CAD:
                return "¢";
            case CHF:
                return "CHF";
            case HKD:
                return "HK$";
            case SGD:
                return "S$";
            case SEK:
                return "kr";
            case KRW:
                return "₩";
            case NOK:
                return "NOK";
            case NZD:
                return "NZ$";
            case INR:
                return "₹";
            case MXN:
                return "Mex$";
            case TWD:
                return "NT$";
            case ZAR:
                return "R";
            case BRL:
                return "R$";
            case DKK:
                return "Dkr";
            case PLN:
                return "zł";
            case THB:
                return "฿";
            case ILS:
                return "₪";
            case IDR:
                return "Rp";
            case CZK:
                return "Kč";
            case AED:
                return "AED";
            case TRY:
                return "₺";
            case HUF:
                return "Ft";
            case CLP:
                return "CLP$";
            case SAR:
                return "﷼";
            case PHP:
                return "₱";
            case MYR:
                return "RM";
            case COP:
                return "Col$";
            case RUB:
                return "₽";
            case RON:
                return "lei";
            case PEN:
                return "S/.";
            case BHD:
                return "BD";
            case BGN:
                return "лв";
            default:
                return "€";

        }
    }
}
