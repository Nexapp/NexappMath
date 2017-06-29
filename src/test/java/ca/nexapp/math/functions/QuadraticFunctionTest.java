package ca.nexapp.math.functions;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class QuadraticFunctionTest {

    private static final double TOLERANCE = 0.00001;

    @Test
    public void givenNoRoot_ShouldHaveNoRealRoot() {
        // http://www.wolframalpha.com/input/?i=2x%C2%B2+%2B+-4x+%2B+7
        QuadraticFunction quadratic = new QuadraticFunction(2, -4, 7);

        double[] roots = quadratic.findRealRoots();

        assertThat(roots).isEmpty();
    }

    @Test
    public void givenNoRoot_ShouldNotHaveRoots() {
        // http://www.wolframalpha.com/input/?i=2x%C2%B2+%2B+-4x+%2B+7
        QuadraticFunction quadratic = new QuadraticFunction(2, -4, 7);

        assertThat(quadratic.hasRoots()).isFalse();
    }

    @Test
    public void givenOneRoot_ShouldHaveRoots() {
        // http://www.wolframalpha.com/input/?i=1x%C2%B2+%2B+-12x+%2B+36
        QuadraticFunction quadratic = new QuadraticFunction(1, -12, 36);

        assertThat(quadratic.hasRoots()).isTrue();
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

    @Test
    public void givenOneRoot_TheLeftRootIsTheSingleRoot() {
        // http://www.wolframalpha.com/input/?i=1x%C2%B2+%2B+-12x+%2B+36
        QuadraticFunction quadratic = new QuadraticFunction(1, -12, 36);

        assertThat(quadratic.findLeftRoot()).isWithin(TOLERANCE).of(6.0);
    }

    @Test
    public void givenOneRoot_TheRightRootIsTheSingleRoot() {
        // http://www.wolframalpha.com/input/?i=1x%C2%B2+%2B+-12x+%2B+36
        QuadraticFunction quadratic = new QuadraticFunction(1, -12, 36);

        assertThat(quadratic.findRightRoot()).isWithin(TOLERANCE).of(6.0);
    }

    @Test
    public void givenTwoRoots_CanFindTheMostLeftRoot() {
        // http://www.wolframalpha.com/input/?i=1x%C2%B2+%2B+2x+-+8
        QuadraticFunction quadratic = new QuadraticFunction(1, 2, -8);

        assertThat(quadratic.findLeftRoot()).isWithin(TOLERANCE).of(-4.0);
    }

    @Test
    public void givenTwoRoots_CanFindTheMostRightRoot() {
        // http://www.wolframalpha.com/input/?i=1x%C2%B2+%2B+2x+-+8
        QuadraticFunction quadratic = new QuadraticFunction(1, 2, -8);

        assertThat(quadratic.findRightRoot()).isWithin(TOLERANCE).of(2.0);
    }
}
