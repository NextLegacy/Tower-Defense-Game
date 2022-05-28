package engine.math;

public final class MathUtility
{
    public final static double EPSILON = 1e-16;
    public final static double PI      = java.lang.Math.PI;
    public final static double E       = java.lang.Math.E;

    public final static double DEGREE  = 180 / PI;
    public final static double RADIANT = PI / 180;

    public static double MAX(double... values)
    {
        double result = values[0];

        for (int i = 1; i < values.length; i++)
            if (values[i] > result)
                result = values[i];

        return result;
    }

    public static double MIN(double... values)
    {
        double result = values[0];

        for (int i = 1; i < values.length; i++)
            if (values[i] < result)
                result = values[i];

        return result;
    }

    public static double SIGN(double n) { return n >= 0 ? 1 : -1; }

    public static double ABS(double n) { return n < 0 ? -n : n; }

    public static double FLOOR(double n) { return (int) n; }
    public static double ROUND(double n) { return FLOOR(n + SIGN(n) * 0.5); }

    public static boolean IS_IN_RANGE(double n, double min, double max) { return n >= min && n <= max; }
    public static double CLAMP(double n, double min, double max) { return n <= min ? min : n >= max ? max : n; }

    public static double LERP(double a, double b, double t)
    {
        return t * (b - a) + a;
    }
}