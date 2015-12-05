package ca.nexapp.math;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PercentageTest {

    private static final double TOLERANCE = 0.00000001;

    @Test
    public void givenZeroPercentFromRatio_ShouldReturnZeroPercent() {
        Percentage zeroPercent = Percentage.fromRatio(0.0);

        assertThat(zeroPercent).isEqualTo(Percentage.ZERO_PERCENT);
    }

    @Test
    public void givenOneHundredPercentFromFraction_ShouldReturnOneHundredPercent() {
        Percentage oneHundredPercentage = Percentage.fromFraction(1.0);

        assertThat(oneHundredPercentage).isEqualTo(Percentage.ONE_HUNDRED_PERCENT);
    }

    @Test
    public void givenOneHundredPercent_ShouldReturnOneAsFraction() {
        assertThat(Percentage.ONE_HUNDRED_PERCENT.toFraction()).isWithin(TOLERANCE).of(1.0);
    }

    @Test
    public void givenOneHundredPercent_ShouldReturnOneHundredAsRatio() {
        assertThat(Percentage.ONE_HUNDRED_PERCENT.toRatio()).isWithin(TOLERANCE).of(100.0);
    }
}
