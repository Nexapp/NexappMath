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

    @Test
    public void canCreateAPercentageWithANumeratorAndADenominator() {
	Percentage percentage = Percentage.fromFraction(10, 40);

	Percentage expected = Percentage.fromRatio(25);
	assertThat(percentage).isEqualTo(expected);
    }

    @Test
    public void canCalculateTheAverageBetweenTwoValues() {
	Percentage average = Percentage.ZERO_PERCENT.average(Percentage.ONE_HUNDRED_PERCENT);

	Percentage expected = Percentage.fromRatio(50);
	assertThat(average).isEqualTo(expected);
    }

    @Test
    public void canCalculateTheAverageBetweenMultipleValues() {
	Percentage value1 = Percentage.fromRatio(60);
	Percentage value2 = Percentage.fromRatio(75);
	Percentage value3 = Percentage.fromRatio(75);
	Percentage value4 = Percentage.fromRatio(90);
	Percentage value5 = Percentage.fromRatio(75);

	Percentage average = value1.average(value2, value3, value4, value5);

	Percentage expected = Percentage.fromRatio(75);
	assertThat(average).isEqualTo(expected);
    }
}
