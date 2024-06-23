package sigmabank.utils;

import java.math.MathContext;
import java.math.RoundingMode;

public class Precision {
    public static final MathContext MATH_CONTEXT = new MathContext(10, RoundingMode.HALF_UP);
}
