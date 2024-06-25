package sigmabank.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class Rounder {

    /**
     * 
     * @param value
     * @return
     * @throws IllegalArgumentException
     */
    public static BigDecimal round(BigDecimal value) throws IllegalArgumentException{
        return new BigDecimal(value.setScale(2, RoundingMode.HALF_UP).toString());
    }

    /**
     * 
     * @param value
     * @param scale
     * @return
     * @throws IllegalArgumentException
     */
    public static BigDecimal round(BigDecimal value, int scale) throws IllegalArgumentException{
        return new BigDecimal(value.setScale(scale, RoundingMode.HALF_UP).toString());
    }

    /**
     * 
     * @param uuid
     * @return
     */
    public static String round(UUID uuid) {
        return uuid.toString().split("-")[0];
    }
}
