package ca.nexapp.math.functions;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import ca.nexapp.math.shapes.Ellipse;

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

    public QuarticFunction(Ellipse ellipse1, Ellipse ellipse2) {
        // https://www.khanacademy.org/computer-programming/ellipse-collision-detector/5514890244521984
        // https://elliotnoma.wordpress.com/2013/04/10/a-closed-form-solution-for-the-intersections-of-two-ellipses/
        double[] terms1 = ellipse1.toStandardForm();
        double[] terms2 = ellipse2.toStandardForm();
        double a = terms1[0];
        double b = terms1[1];
        double c = terms1[2];
        double d = terms1[3];
        double e = terms1[4];
        double f = terms1[5];
        double a1 = terms2[0];
        double b1 = terms2[1];
        double c1 = terms2[2];
        double d1 = terms2[3];
        double e1 = terms2[4];
        double fq = terms2[5];

        this.e = f * a * d1 * d1 + a * a * fq * fq - d * a * d1 * fq + a1 * a1 * f * f - 2.0 * a * fq * a1 * f - d * d1 * a1 * f + a1 * d
                * d * fq;
        this.d = e1 * d * d * a1 - fq * d1 * a * b - 2.0 * a * fq * a1 * e - f * a1 * b1 * d + 2.0 * d1 * b1 * a * f + 2.0 * e1 * fq * a
                * a + d1 * d1 * a * e - e1 * d1 * a * d - 2.0 * a * e1 * a1 * f - f * a1 * d1 * b + 2.0 * f * e * a1 * a1 - fq * b1 * a * d
                - e * a1 * d1 * d + 2.0 * fq * b * a1 * d;
        this.c = e1 * e1 * a * a + 2.0 * c1 * fq * a * a - e * a1 * d1 * b + fq * a1 * b * b - e * a1 * b1 * d - fq * b1 * a * b - 2.0 * a
                * e1 * a1 * e + 2.0 * d1 * b1 * a * e - c1 * d1 * a * d - 2.0 * a * c1 * a1 * f + b1 * b1 * a * f + 2.0 * e1 * b * a1 * d
                + e * e * a1 * a1 - c * a1 * d1 * d - e1 * b1 * a * d + 2.0 * f * c * a1 * a1 - f * a1 * b1 * b + c1 * d * d * a1 + d1 * d1
                * a * c - e1 * d1 * a * b - 2.0 * a * fq * a1 * c;
        this.b = -2.0 * a * a1 * c * e1 + e1 * a1 * b * b + 2.0 * c1 * b * a1 * d - c * a1 * b1 * d + b1 * b1 * a * e - e1 * b1 * a * b
                - 2.0 * a * c1 * a1 * e - e * a1 * b1 * b - c1 * b1 * a * d + 2.0 * e1 * c1 * a * a + 2.0 * e * c * a1 * a1 - c * a1 * d1
                * b + 2.0 * d1 * b1 * a * c - c1 * d1 * a * b;
        this.a = a * a * c1 * c1 - 2.0 * a * c1 * a1 * c + a1 * a1 * c * c - b * a * b1 * c1 - b * b1 * a1 * c + b * b * a1 * c1 + c * a
                * b1 * b1;
    }

    public final boolean hasRoots() {
        // https://www.khanacademy.org/computer-programming/dr-daves-ellipse-collision-detector/5776508379463680
        if (e == 0.0) {
            return true; // zero is a root
        }
        if (a == 0.0) {
            if (b != 0.0) {
                return true; // cubics always have roots
            }
            if (c != 0.0) {
                return new QuadraticFunction(c, d, e).hasRoots();
            }
            return d != 0.0; // sloped lines have one root
        }

        QuarticFunction depressedQuartic = toDepressed();
        double a = depressedQuartic.a;
        double b = depressedQuartic.b;
        double c = depressedQuartic.c;
        double d = depressedQuartic.d;
        double e = depressedQuartic.e;

        double p = 8.0 * a * c - 3.0 * b * b;
        double d1 = 64.0 * a * a * a * e - 16.0 * a * a * c * c + 16.0 * a * b * b * c - 16.0 * a * a * b * d - 3.0 * b * b * b * b;
        double discriminant = depressedQuartic.getDiscriminant();

        return discriminant < 0.0 || discriminant > 0.0 && p <= 0.0 && d1 <= 0.0 || discriminant == 0.0 && (d1 != 0.0 || p <= 0.0);
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


    public double getDiscriminant() {
        // http://en.wikipedia.org/wiki/Quartic_function#Solving_a_quartic_equation
        return 256.0 * a * a * a * e * e * e - 192.0 * a * a * b * d * e * e - 128.0 * a * a * c * c * e * e + 144.0 * a * a * c * d * d
                * e - 27.0 * a * a * d * d * d * d + 144.0 * a * b * b * c * e * e - 6.0 * a * b * b * d * d * e - 80.0 * a * b * c * c * d
                * e + 18.0 * a * b * c * d * d * d + 16.0 * a * c * c * c * c * e - 4 * a * c * c * c * d * d - 27.0 * b * b * b * b * e
                * e + 18.0 * b * b * b * c * d * e - 4.0 * b * b * b * d * d * d - 4.0 * b * b * c * c * c * e + b * b * c * c * d * d;
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
