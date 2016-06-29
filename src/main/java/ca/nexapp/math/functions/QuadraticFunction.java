package ca.nexapp.math.functions;

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

    private double getDiscriminant() {
        return b * b - 4.0 * a * c;
    }

    @Override
    public String toString() {
        return "y = " + a + "x² + " + b + "x + " + c;
    }
}
