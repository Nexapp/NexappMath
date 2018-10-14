package ca.nexapp.math.functions;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class QuarticFunction {

    private static final double NEAR_ZERO = 0.0000001;

    private final double a;
    private final double b;
    private final double c;
    private final double d;
    private final double e;

    public QuarticFunction(double a, double b, double c, double d, double e) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
    }

    public double[] findRealRoots() {
        if (abs(a) < NEAR_ZERO) {
            return new CubicFunction(b, c, d, e).findRealRoots();
        }

        if (isBiquadratic()) {
            return solveUsingBiquadraticMethod();
        }

        return solveUsingFerrariMethodWikipedia();
    }

    public boolean isBiquadratic() {
        return abs(b) < NEAR_ZERO && abs(d) < NEAR_ZERO;
    }

    private double[] solveUsingBiquadraticMethod() {
        QuadraticFunction quadratic = new QuadraticFunction(a, c, e);
        double[] quadraticRoots = quadratic.findRealRoots();
        Set<Double> roots = new HashSet<>();
        for (double quadraticRoot : quadraticRoots) {
            if (quadraticRoot > 0.0) {
                roots.add(sqrt(quadraticRoot));
                roots.add(-sqrt(quadraticRoot));
            } else if (quadraticRoot == 0.00) {
                roots.add(0.00);
            }
        }
        return roots.stream().mapToDouble(e -> e).toArray();
    }

    private double[] solveUsingFerrariMethodWikipedia() {
        // http://en.wikipedia.org/wiki/Quartic_function#Ferrari.27s_solution
        QuarticFunction depressed = toDepressed();
        if (depressed.isBiquadratic()) {
            double[] depressedRoots = depressed.solveUsingBiquadraticMethod();
            return reconvertToOriginalRoots(depressedRoots);
        }

        double y = findFerraryY(depressed);
        double originalRootConversionPart = -b / (4.0 * a);
        double firstPart = sqrt(depressed.c + 2.0 * y);

        double positiveSecondPart = sqrt(-(3.0 * depressed.c + 2.0 * y + 2.0 * depressed.d / sqrt(depressed.c + 2.0 * y)));
        double negativeSecondPart = sqrt(-(3.0 * depressed.c + 2.0 * y - 2.0 * depressed.d / sqrt(depressed.c + 2.0 * y)));

        double x1 = originalRootConversionPart + (firstPart + positiveSecondPart) / 2.0;
        double x2 = originalRootConversionPart + (-firstPart + negativeSecondPart) / 2.0;
        double x3 = originalRootConversionPart + (firstPart - positiveSecondPart) / 2.0;
        double x4 = originalRootConversionPart + (-firstPart - negativeSecondPart) / 2.0;

        Set<Double> realRoots = findOnlyRealRoots(x1, x2, x3, x4);
        return realRoots.stream().mapToDouble(e -> e).toArray();
    }

    public QuarticFunction toDepressed() {
        // http://en.wikipedia.org/wiki/Quartic_function#Converting_to_a_depressed_quartic
        double p = (8.0 * a * c - 3.0 * pow(b, 2.0)) / (8.0 * pow(a, 2.0));
        double q = (pow(b, 3.0) - 4.0 * a * b * c + 8.0 * d * pow(a, 2.0)) / (8.0 * pow(a, 3.0));
        double r = (-3.0 * pow(b, 4.0) + 256.0 * e * pow(a, 3.0) - 64.0 * d * b * pow(a, 2.0) + 16.0 * c * a * pow(b, 2.0))
                / (256.0 * pow(a, 4.0));
        return new QuarticFunction(1.0, 0.0, p, q, r);
    }

    private double[] reconvertToOriginalRoots(double[] depressedRoots) {
        double[] originalRoots = new double[depressedRoots.length];
        for (int i = 0; i < depressedRoots.length; ++i) {
            originalRoots[i] = depressedRoots[i] - b / (4.0 * a);
        }
        return originalRoots;
    }

    private double findFerraryY(QuarticFunction depressedQuartic) {
        double a3 = 1.0;
        double a2 = 5.0 / 2.0 * depressedQuartic.c;
        double a1 = 2.0 * pow(depressedQuartic.c, 2.0) - depressedQuartic.e;
        double a0 = pow(depressedQuartic.c, 3.0) / 2.0 - depressedQuartic.c * depressedQuartic.e / 2.0 - pow(depressedQuartic.d, 2.0) / 8.0;

        CubicFunction cubicFunction = new CubicFunction(a3, a2, a1, a0);
        double[] roots = cubicFunction.findRealRoots();

        for (double y : roots) {
            if (depressedQuartic.c + 2.0 * y != 0.0) {
                return y;
            }
        }
        throw new IllegalStateException("Ferrari method should have at least one y");
    }

    private Set<Double> findOnlyRealRoots(double... roots) {
        Set<Double> realRoots = new HashSet<>();
        for (double root : roots) {
            if (Double.isFinite(root)) {
                double actualRoot = roundIfNearZero(root);
                realRoots.add(actualRoot);
            }
        }
        return realRoots;
    }

    private double roundIfNearZero(double value) {
        if (abs(value) < 0.01) {
            return round(value);
        }
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c, d, e);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof QuarticFunction)) {
            return false;
        }

        QuarticFunction other = (QuarticFunction) obj;
        return Objects.equals(a, other.a)
                && Objects.equals(b, other.b)
                && Objects.equals(c, other.c)
                && Objects.equals(d, other.d)
                && Objects.equals(e, other.e);
    }

    @Override
    public String toString() {
        return "y = " + a + "x^4 + " + b + "x³ + " + c + "x² + " + d + "x + " + e;
    }
}
