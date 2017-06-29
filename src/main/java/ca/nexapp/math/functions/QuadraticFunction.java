package ca.nexapp.math.functions;

import java.util.Objects;

public class QuadraticFunction implements Solvable {

    private final double a;
    private final double b;
    private final double c;

    public QuadraticFunction(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double[] findRealRoots() {
        double discriminant = getDiscriminant();

        if (discriminant < 0) {
            return new double[] {};
        } else if (discriminant == 0) {
            double root = -b / (2.0 * a);
            return new double[] { root };
        } else {
            double positiveRoot = (-b + Math.sqrt(discriminant)) / (2.0 * a);
            double negativeRoot = (-b - Math.sqrt(discriminant)) / (2.0 * a);
            return new double[] { positiveRoot, negativeRoot };
        }
    }

    public boolean hasRoots() {
        return getDiscriminant() >= 0;
    }

    public double findRightRoot() {
        double[] roots = findRealRoots();
        return roots.length == 1 ? roots[0] : Math.max(roots[0], roots[1]);
    }

    public double findLeftRoot() {
        double[] roots = findRealRoots();
        return roots.length == 1 ? roots[0] : Math.min(roots[0], roots[1]);
    }

    private double getDiscriminant() {
        return b * b - 4.0 * a * c;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof QuadraticFunction)) {
            return false;
        }

        QuadraticFunction other = (QuadraticFunction) obj;
        return Objects.equals(a, other.a)
                && Objects.equals(b, other.b)
                && Objects.equals(c, other.c);
    }

    @Override
    public String toString() {
        return "y = " + a + "x² + " + b + "x + " + c;
    }
}
