package ca.nexapp.math.functions;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

import ca.nexapp.math.units.Point;

public class QuadraticFunctionTest {

    private static final double TOLERANCE = 0.00001;

    @Test
    public void canSolveAQuadraticFunctionFromThreePoints() {
        Point a = Point.fromCartesian(20, 0);
        Point b = Point.fromCartesian(35, 35);
        Point c = Point.fromCartesian(70, 0);
        QuadraticFunction quadratic = QuadraticFunction.fromThreePoints(a, b, c);

        double[] roots = quadratic.findRealRoots();

        double[] expected = { 20, 70 };
        assertThat(roots).hasValuesWithin(TOLERANCE).of(expected);
    }

    @Test
    public void canSolveAQuadraticFunctionFromVertexAndAPoint() {
        Point vertex = Point.fromCartesian(45.00, 41.66666666666666);
        Point point = Point.fromCartesian(20, 0);
        QuadraticFunction quadratic = QuadraticFunction.fromVertex(vertex, point);

        double[] roots = quadratic.findRealRoots();

        double[] expected = { 20, 70 };
        assertThat(roots).hasValuesWithin(TOLERANCE).of(expected);
    }

    @Test
    public void givenX_CanFindY() {
        QuadraticFunction quadratic = new QuadraticFunction(3, 5, 6);

        double y = quadratic.findY(4);

        assertThat(y).isWithin(TOLERANCE).of(74);
    }

    @Test
    public void canFindTheVertex() {
        Point a = Point.fromCartesian(20, 0);
        Point b = Point.fromCartesian(35, 35);
        Point c = Point.fromCartesian(70, 0);
        QuadraticFunction quadratic = QuadraticFunction.fromThreePoints(a, b, c);

        Point vertex = quadratic.findVertex();

        assertThat(vertex).isEqualTo(Point.fromCartesian(45, 41.66666666666666));
    }

    @Test
    public void givenNoRoot_ShouldHaveNoRealRoot() {
        // http://www.wolframalpha.com/input/?i=2x%C2%B2+%2B+-4x+%2B+7
        QuadraticFunction quadratic = new QuadraticFunction(2, -4, 7);

        double[] roots = quadratic.findRealRoots();

        assertThat(roots).isEmpty();
    }

    @Test
    public void givenOneRoot_ShouldFindTheRealRoot() {
        // http://www.wolframalpha.com/input/?i=1x%C2%B2+%2B+-12x+%2B+36
        QuadraticFunction quadratic = new QuadraticFunction(1, -12, 36);

        double[] roots = quadratic.findRealRoots();

        double[] expected = { 6.0 };
        assertThat(roots).hasValuesWithin(TOLERANCE).of(expected);
    }

    @Test
    public void givenTwoRoots_ShouldFindTheRealRoots() {
        // http://www.wolframalpha.com/input/?i=1x%C2%B2+%2B+2x+-+8
        QuadraticFunction quadratic = new QuadraticFunction(1, 2, -8);

        double[] roots = quadratic.findRealRoots();

        double[] expected = { 2.0, -4.0 };
        assertThat(roots).hasValuesWithin(TOLERANCE).of(expected);
    }
}
