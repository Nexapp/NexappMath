package ca.nexapp.math;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PercentageTest {

    private static final double ZERO = 0.00000;
    private static final double ONE = 1.000000;
    private static final double ONE_HUNDRED = 100.000000;

    private static final double TOLERANCE = 0.00000001;

    @Test
    public void givenZeroPercentFromRatio_ShouldReturnZeroPercent() {
        Percentage zeroPercent = Percentage.fromRatio(ZERO);

        assertThat(zeroPercent).isEqualTo(Percentage.ZERO_PERCENT);
    }

    @Test
    public void givenOneHundredPercentFromFraction_ShouldReturnOneHundredPercent() {
        Percentage oneHundredPercentage = Percentage.fromFraction(ONE);

        assertThat(oneHundredPercentage).isEqualTo(Percentage.ONE_HUNDRED_PERCENT);
    }

    @Test
    public void givenOneHundredPercent_ShouldReturnOneAsFraction() {
        assertThat(Percentage.ONE_HUNDRED_PERCENT.toFraction()).isWithin(TOLERANCE).of(ONE);
    }

    @Test
    public void givenOneHundredPercent_ShouldReturnOneHundredAsRatio() {
        assertThat(Percentage.ONE_HUNDRED_PERCENT.toRatio()).isWithin(TOLERANCE).of(ONE_HUNDRED);
    }
}
