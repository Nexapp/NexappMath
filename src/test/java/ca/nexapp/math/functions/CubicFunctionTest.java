package ca.nexapp.math.functions;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class CubicFunctionTest {

    private static final double TOLERANCE = 0.1;

    @Test
    public void givenOneRoot_ShouldFindTheSingleRealRoot() {
        // http://www.wolframalpha.com/input/?i=1x%C2%B3+%2B+0x%C2%B2+%2B+0x+%2B+0+%3D+0
        CubicFunction cubic = new CubicFunction(1, 0, 0, 0);

        double roots[] = cubic.findRealRoots();

        double[] expected = { 0 };
        assertThat(roots).hasValuesWithin(TOLERANCE).of(expected);
    }

    @Test
    public void givenOneRealRoot_ShouldFindTheSingleRealRoot() {
        // http://www.wolframalpha.com/input/?i=3x%C2%B3+%2B+-10x%C2%B2+%2B+14x+%2B+27+%3D+0
        CubicFunction cubic = new CubicFunction(3, -10, 14, 27);

        double roots[] = cubic.findRealRoots();

        double[] expected = { -1.0 };
        assertThat(roots).hasValuesWithin(TOLERANCE).of(expected);
    }

    @Test
    public void givenThreeDistinctRealRoots_ShouldFindTheRealRoots() {
        // http://www.wolframalpha.com/input/?i=2x%C2%B3+%2B+-4x%C2%B2+%2B+-22x+%2B+24+%3D+0
        CubicFunction cubic = new CubicFunction(2, -4, -22, 24);

        double roots[] = cubic.findRealRoots();

        double[] expected = { 4, -3, 1 };
        assertThat(roots).hasValuesWithin(TOLERANCE).of(expected);
    }

    @Test
    public void givenThreeRealAndEqualsRoots_ShouldFindTheSingleRealRoot() {
        // http://www.wolframalpha.com/input/?i=1x%C2%B3+%2B+6x%C2%B2+%2B+12x+%2B+8+%3D+0
        CubicFunction cubic = new CubicFunction(1, 6, 12, 8);

        double roots[] = cubic.findRealRoots();

        double[] expected = { -2 };
        assertThat(roots).hasValuesWithin(TOLERANCE).of(expected);
    }

    @Test
    public void givenOneRootWithVeryHighParameters_ShouldFindTheSingleRealRoot() {
        // http://www.wolframalpha.com/input/?i=solve%281*x^3+%2B+112398.4458838065+*+x^2+%2B+4.0712054820915356E9+*+x+%2B+4.6043940766539016E13+%3D+0%29
        CubicFunction cubic = new CubicFunction(1.0, 112398.4458838065, 4.0712054820915356 * Math.pow(10, 9),
                4.6043940766539016 * Math.pow(10, 13));

        double roots[] = cubic.findRealRoots();

        double[] expected = { -22412.1 };
        assertThat(roots).hasValuesWithin(TOLERANCE).of(expected);
    }
}
