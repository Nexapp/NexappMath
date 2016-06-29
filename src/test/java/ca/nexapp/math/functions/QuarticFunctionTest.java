package ca.nexapp.math.functions;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class QuarticFunctionTest {

    private static final double A_VALUE = 66.0;
    private static final double TOLERANCE = 0.1;

    @Test
    public void givenOnlyBValuedATZero_ShouldNotBeBiquadratic() {
        QuarticFunction quartic = new QuarticFunction(A_VALUE, 0, A_VALUE, A_VALUE, A_VALUE);

        assertThat(quartic.isBiquadratic()).isFalse();
    }

    @Test
    public void givenOnlyDValuedATZero_ShouldNotBeBiquadratic() {
        QuarticFunction quartic = new QuarticFunction(A_VALUE, A_VALUE, A_VALUE, 0, A_VALUE);

        assertThat(quartic.isBiquadratic()).isFalse();
    }

    @Test
    public void givenBAndDValuedATZero_ShouldBeBiquadratic() {
        QuarticFunction quartic = new QuarticFunction(A_VALUE, 0, A_VALUE, 0, A_VALUE);

        assertThat(quartic.isBiquadratic()).isTrue();
    }

    @Test
    public void givenOneRealRoot_ShouldFindTheSingleRealRoot() {
        QuarticFunction quarticFunction = new QuarticFunction(1.0, 0.0, 0.0, 0.0, 0.0);

        double[] roots = quarticFunction.findRealRoots();

        double[] expected = { 0 };
        assertThat(roots).hasValuesWithin(TOLERANCE).of(expected);
    }

    @Test
    public void givenTwoRealAndTwoComplexRoots_ShouldFindTheTwoRealRoots() {
        // http://www.1728.org/quartic2.htm
        QuarticFunction quarticFunction = new QuarticFunction(-20.0, 5.0, 17.0, -29.0, 87.0);

        double[] roots = quarticFunction.findRealRoots();

        double[] expected = { 1.48758311033, -1.68200392658 };
        assertThat(roots).hasValuesWithin(TOLERANCE).of(expected);
    }

    @Test
    public void givenFourRealRoots_ShouldFindTheFourRealRoots() {
        // http://www.1728.org/quartic2.htm
        QuarticFunction quarticFunction = new QuarticFunction(3.0, 6.0, -123.0, -126.0, 1080.0);

        double[] roots = quarticFunction.findRealRoots();

        double[] expected = { 5.00, -6.00, 3.00, -4.00 };
        assertThat(roots).hasValuesWithin(TOLERANCE).of(expected);
    }

    @Test
    public void givenAnotherCaseOfFourRealRoots_ShouldFindTheFourRealRoots() {
        // http://www.wolframalpha.com/input/?i=3x%5E4+%2B+6x%C2%B3+%2B+-123x%C2%B2+%2B+-124x+%2B+1080+%3D+0
        QuarticFunction quarticFunction = new QuarticFunction(3.0, 6.0, -123.0, -124.0, 1080.0);

        double[] roots = quarticFunction.findRealRoots();

        double[] expected = { -6.0200, 3.0160, -3.9791, 4.9830 };
        assertThat(roots).hasValuesWithin(TOLERANCE).of(expected);
    }

    @Test
    public void givenThreeRealRoots_ShouldFindTheThreeRealRoots() {
        // http://www.wolframalpha.com/input/?i=x%5E4+%2B+x%C2%B3+%2B+-300x%C2%B2+%2B+-x+%2B+0+%3D+0
        QuarticFunction quarticFunction = new QuarticFunction(1.0, 1.0, -300.0, -1.0, 0.0);

        double[] roots = quarticFunction.findRealRoots();

        double[] expected = { 0.00, -17.826, 16.829 };
        assertThat(roots).hasValuesWithin(TOLERANCE).of(expected);
    }

    @Test
    public void givenBiquadraticQuarticWithTwoRealRoots_ShouldFindTheTwoRealRoots() {
        // http://www.wolframalpha.com/input/?i=1x%5E4+%2B+0x%C2%B3+%2B+2x%C2%B2+%2B+0x+%2B+-2+%3D+0
        QuarticFunction biquadraticQuartic = new QuarticFunction(1.0, 0.0, 2.0, 0.0, -2.0);

        double[] roots = biquadraticQuartic.findRealRoots();

        double[] expected = { Math.sqrt(Math.sqrt(3) - 1), -Math.sqrt(Math.sqrt(3) - 1) };
        assertThat(roots).hasValuesWithin(TOLERANCE).of(expected);
    }
}
