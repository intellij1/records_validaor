package com.rabobank.util;


import java.math.BigDecimal;
import java.util.Set;

/**
 * This is a utility class for Record processing .
 * <p>
 * *
 */
public interface RecordUtil {


    static BigDecimal strToBigDecimal(String str) {

        return null != str ? new BigDecimal(str) : BigDecimal.ZERO;
    }


    static boolean isRefIDNumeric(String refNumber) {
        return refNumber.chars().allMatch(Character::isDigit);

    }


    static boolean isRefNumberUnique(String refNumber, Set<String> refIDSet) {
        boolean retVal = true;

        if (refIDSet.contains(refNumber)) {
            retVal = false;
        } else {
            refIDSet.add(refNumber);
        }

        return retVal;
    }
}
