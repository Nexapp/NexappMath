package ca.nexapp.math.functions;

import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import org.apache.commons.math3.complex.Complex;

public class CubicFunction implements Solvable {

    private final double a;
    private final double b;
    private final double c;
    private final double d;

    public CubicFunction(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    @Override
    public double[] findRealRoots() {
        // Solution found at: http://www.1728.org/cubic2.htm
        double f = (3.0 * c / a - pow(b, 2.0) / pow(a, 2.0)) / 3.0;
        double g = (2.0 * pow(b, 3.0) / pow(a, 3.0) - 9.0 * b * c / pow(a, 2.0) + 27.0 * d / a) / 27.0;
        double h = pow(g, 2.0) / 4.0 + pow(f, 3.0) / 27.0;

        if (hasOneRealAndTwoComplexRoots(h)) {
            double[] allRoots = findOneRealAndTwoComplexRoots(g, h);
            return new double[] { allRoots[0] };
        } else if (hasThreeRealAndEqualsRoots(f, g, h)) {
            double[] allRoots = findThreeRealAndEqualsRoots();
            return new double[] { allRoots[0] };
        } else {
            return findThreeRealAndDistinctRoots(g, h);
        }
    }

    private boolean hasOneRealAndTwoComplexRoots(double h) {
        return h > 0.0;
    }

    private boolean hasThreeRealAndEqualsRoots(double f, double g, double h) {
        return f == 0.0 && g == 0.0 && h == 0.0;
    }

    private double[] findOneRealAndTwoComplexRoots(double g, double h) {
        double r = -(g / 2.0) + pow(h, 1.00 / 2.00);
        double s = pow(r, 1.00 / 3.00);
        double t = -(g / 2.0) - pow(h, 1.00 / 2.00);
        double u = new Complex(t).pow(1.00 / 3.00).abs();

        if (t < 0) {
            u = u * -1;
        }

        double realRoot = s + u - b / (3.0 * a);
        double complexRoot = -(s + u) / 2.0 - b / (3.0 * a);
        return new double[] { realRoot, complexRoot, complexRoot };
    }

    private double[] findThreeRealAndEqualsRoots() {
        double root = pow(d / a, 1.00 / 3.00) * -1.0;
        return new double[] { root, root, root };
    }

    private double[] findThreeRealAndDistinctRoots(double g, double h) {
        double i = pow(pow(g, 2.0) / 4.0 - h, 1.00 / 2.00);
        double j = pow(i, 1.00 / 3.00);
        double k = acos(-(g / (2.0 * i)));
        double l = j * -1.0;
        double m = cos(k / 3.0);
        double n = sqrt(3.0) * sin(k / 3.0);
        double p = b / (3.0 * a) * -1.0;

        double x1 = 2.0 * j * cos(k / 3.0) - b / (3.0 * a);
        double x2 = l * (m + n) + p;
        double x3 = l * (m - n) + p;

        return new double[] { x1, x2, x3 };
    }

    @Override
    public String toString() {
        return "y = " + a + "x³ + " + b + "x² + " + c + "x + " + d;
    }
}
