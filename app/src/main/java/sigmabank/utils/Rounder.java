package sigmabank.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Rounder {
    public static BigDecimal round(BigDecimal value) throws IllegalArgumentException{
        return new BigDecimal(value.setScale(2, RoundingMode.HALF_UP).toString());
    }

    public static BigDecimal round(BigDecimal value, int scale) throws IllegalArgumentException{
        return new BigDecimal(value.setScale(scale, RoundingMode.HALF_UP).toString());
    }
}
