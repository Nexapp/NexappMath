package ca.nexapp.math;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DurationTest {

    @Test
    public void addingTwoSecondsToZero_ShouldReturnTwoSeconds() {
        Duration twoSeconds = Duration.seconds(2);

        assertThat(Duration.seconds(0).add(twoSeconds)).isEqualTo(twoSeconds);
    }

    @Test
    public void substractingTwoSecondsToTwoSeconds_ShouldReturnZeroSeconds() {
        Duration twoSeconds = Duration.seconds(2);

        assertThat(twoSeconds.substract(twoSeconds)).isEqualTo(Duration.seconds(0));
    }

    @Test
    public void givenSeventyNanoseconds_ShouldReturnSeventyNanoseconds() {
        assertThat(Duration.nanoseconds(70).toNanoseconds()).isEqualTo(70);
    }

    @Test
    public void givenOneThousandNanoseconds_ShouldReturnOneMicrosecond() {
        assertThat(Duration.nanoseconds(1000).toMicroseconds()).isEqualTo(1);
    }

    @Test
    public void givenSixtyHundredsMicroseconds_ShouldReturnSixtyHundredsMicroseconds() {
        assertThat(Duration.microseconds(600).toMicroseconds()).isEqualTo(600);
    }

    @Test
    public void givenOneThousandMicroseconds_ShouldReturnOneMillisecond() {
        assertThat(Duration.microseconds(1000).toMilliseconds()).isEqualTo(1);
    }

    @Test
    public void givenFiveHundredMilliseconds_ShouldReturnFiveHundredMilliseconds() {
        assertThat(Duration.milliseconds(500).toMilliseconds()).isEqualTo(500);
    }

    @Test
    public void givenOneThousandMilliseconds_ShouldReturnOneSecond() {
        assertThat(Duration.milliseconds(1000).toSeconds()).isEqualTo(1);
    }

    @Test
    public void givenThirtySeconds_ShouldReturnThirySeconds() {
        assertThat(Duration.seconds(30).toSeconds()).isEqualTo(30);
    }

    @Test
    public void givenSixtySeconds_ShouldReturnOneMinute() {
        assertThat(Duration.seconds(60).toMinutes()).isEqualTo(1);
    }

    @Test
    public void given3600Seconds_ShouldReturnOneHour() {
        assertThat(Duration.seconds(3600).toHours()).isEqualTo(1);
    }

    @Test
    public void givenTwentyMinutes_ShouldReturnTwentyMinutes() {
        assertThat(Duration.minutes(20).toMinutes()).isEqualTo(20);
    }

    @Test
    public void givenSixtyMinutes_ShouldReturnOneHour() {
        assertThat(Duration.minutes(60).toHours()).isEqualTo(1);
    }

    @Test
    public void givenThreeHours_ShouldReturnThreeHours() {
        assertThat(Duration.hours(3).toHours()).isEqualTo(3);
    }

    @Test
    public void givenTwentyFourHours_ShouldReturnOneDay() {
        assertThat(Duration.hours(24).toDays()).isEqualTo(1);
    }

    @Test
    public void givenSevenDays_ShouldReturnSevenDays() {
        assertThat(Duration.days(7).toDays()).isEqualTo(7);
    }

    @Test
    public void givenSevenDays_ShouldReturnOneWeek() {
        assertThat(Duration.days(7).toWeeks()).isEqualTo(1);
    }

    @Test
    public void givenTwoWeeks_ShouldReturnTwoWeeks() {
        assertThat(Duration.weeks(2).toWeeks()).isEqualTo(2);
    }

    @Test
    public void givenFiveWeeks_ShouldReturnOneMonth() {
        assertThat(Duration.weeks(5).toMonths()).isEqualTo(1);
    }

    @Test
    public void givenSixtyWeeks_ShouldReturnOneYear() {
        assertThat(Duration.weeks(60).toYears()).isEqualTo(1);
    }

    @Test
    public void givenTwoMonths_ShouldReturnTwoMonths() {
        assertThat(Duration.months(2).toMonths()).isEqualTo(2);
    }

    @Test
    public void givenOneMonth_ShouldReturnFourWeeks() {
        assertThat(Duration.months(1).toWeeks()).isEqualTo(4);
    }

    @Test
    public void givenTwelveMonths_ShouldReturnOneYear() {
        assertThat(Duration.months(12).toYears()).isEqualTo(1);
    }

    @Test
    public void givenThreeYears_ShouldReturnThreeYears() {
        assertThat(Duration.years(3).toYears()).isEqualTo(3);
    }

    @Test
    public void givenOneYear_ShouldReturnTwelveMonths() {
        assertThat(Duration.years(1).toMonths()).isEqualTo(12);
    }

    @Test
    public void givenOneYear_ShouldReturnFiftyTwoWeeks() {
        assertThat(Duration.years(1).toWeeks()).isEqualTo(52);
    }

    @Test
    public void givenOneYear_ShouldReturn365Days() {
        assertThat(Duration.years(1).toDays()).isEqualTo(365);
    }

    @Test
    public void givenTenYears_ShouldReturnOneDecade() {
        assertThat(Duration.years(10).toDecades()).isEqualTo(1);
    }

    @Test
    public void givenOneHundredYears_ShouldReturnOneCentury() {
        assertThat(Duration.years(100).toCenturies()).isEqualTo(1);
    }

    @Test
    public void givenFourDecades_ShouldReturnFourDecades() {
        assertThat(Duration.decades(4).toDecades()).isEqualTo(4);
    }

    @Test
    public void givenOneDecade_ShouldReturnTenYears() {
        assertThat(Duration.decades(1).toYears()).isEqualTo(10);
    }

    @Test
    public void givenTenDecades_ShouldReturnOneCentury() {
        assertThat(Duration.decades(10).toCenturies()).isEqualTo(1);
    }

    @Test
    public void givenTwoCenturies_ShouldReturnTwoCenturies() {
        assertThat(Duration.centuries(1).toCenturies()).isEqualTo(1);
    }

    @Test
    public void givenOneCentury_ShouldReturnTenDecades() {
        assertThat(Duration.centuries(1).toDecades()).isEqualTo(10);
    }

    @Test
    public void givenOneCentury_ShouldReturnOneHundredYears() {
        assertThat(Duration.centuries(1).toYears()).isEqualTo(100);
    }

    @Test
    public void given999Milliseconds_ShouldReturnZeroSecond() {
        assertThat(Duration.milliseconds(999).toSeconds()).isEqualTo(0);
    }

    @Test
    public void givenFiftyNineSeconds_ShouldReturnZeroMinute() {
        assertThat(Duration.seconds(59).toMinutes()).isEqualTo(0);
    }

    @Test
    public void givenFiftyNineMinutes_ShouldReturnZeroHour() {
        assertThat(Duration.minutes(59).toHours()).isEqualTo(0);
    }

    @Test
    public void givenTwentyThreeHours_ShouldReturnZeroDay() {
        assertThat(Duration.hours(23).toDays()).isEqualTo(0);
    }

    @Test
    public void givenSixDays_ShouldReturnZeroWeek() {
        assertThat(Duration.days(6).toWeeks()).isEqualTo(0);
    }

    @Test
    public void givenTwentySevenDays_ShouldReturnZeroMonth() {
        assertThat(Duration.days(27).toMonths()).isEqualTo(0);
    }

    @Test
    public void given364Days_ShouldReturnZeroYear() {
        assertThat(Duration.days(364).toYears()).isEqualTo(0);
    }

    @Test
    public void givenThreeWeeks_ShouldReturnZeroMonth() {
        assertThat(Duration.weeks(3).toMonths()).isEqualTo(0);
    }

    @Test
    public void givenFiftyOneWeeks_ShouldReturnZeroYear() {
        assertThat(Duration.weeks(51).toYears()).isEqualTo(0);
    }

    @Test
    public void givenElevenMonths_ShouldReturnZeroYear() {
        assertThat(Duration.months(11).toYears()).isEqualTo(0);
    }

    @Test
    public void givenNineYears_ShouldReturnZeroDecades() {
        assertThat(Duration.years(9).toDecades()).isEqualTo(0);
    }

    @Test
    public void givenNineDecades_ShouldReturnZeroCentury() {
        assertThat(Duration.decades(9).toCenturies()).isEqualTo(0);
    }

}
