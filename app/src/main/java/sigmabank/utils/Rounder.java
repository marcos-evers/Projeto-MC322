package sigmabank.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Rounder {
    public static BigDecimal round(BigDecimal value, int places) throws IllegalArgumentException{
        if (places < 0) throw new IllegalArgumentException();
        return new BigDecimal(value.setScale(2, RoundingMode.HALF_UP).toString());
    }
}
