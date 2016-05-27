package ca.nexapp.math.units;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class CoordinatesTest {

    @Test
    public void givenNumericLocation_CanRetrieveTheLatitude() {
        Coordinates location = Coordinates.locatedAt(45.34, -73.45);

        assertThat(location.getLatitude()).isEqualTo(Angle.fromDegrees(45.34));
    }

    @Test
    public void givenNumericLocation_CanRetrieveTheLongitude() {
        Coordinates location = Coordinates.locatedAt(45.34, -73.45);

        assertThat(location.getLongitude()).isEqualTo(Angle.fromDegrees(-73.45));
    }

    @Test
    public void givenLocationInAngle_CanRetrieveTheLatitude() {
        Angle latitude = Angle.fromDegrees(39.54);
        Angle longitude = Angle.fromDegrees(-54.76);
        Coordinates location = Coordinates.locatedAt(latitude, longitude);

        assertThat(location.getLatitude()).isEqualTo(latitude);
    }

    @Test
    public void givenLocationInAngle_CanRetrieveTheLongitude() {
        Angle latitude = Angle.fromDegrees(39.54);
        Angle longitude = Angle.fromDegrees(-54.76);
        Coordinates location = Coordinates.locatedAt(latitude, longitude);

        assertThat(location.getLongitude()).isEqualTo(longitude);
    }

    @Test
    public void canCalculateTheDistanceBetweenTwoLocationsOnTheSameContinent() {
        Coordinates centerBellInMontreal = Coordinates.locatedAt(45.4959755, -73.5693904);
        Coordinates yankeeStadiumInNewYork = Coordinates.locatedAt(40.8295818, -73.9261455);

        Length distance = centerBellInMontreal.getDistanceTo(yankeeStadiumInNewYork);

        assertThat(distance.toKilometers()).isWithin(0.5).of(519.23);
    }

    @Test
    public void canCalculateTheDistanceBetweenTwoLocationsOnDifferentContinents() {
        // Example found on http://janmatuschek.de/LatitudeLongitudeBoundingCoordinates
        Coordinates statueOfLiberty = Coordinates.locatedAt(40.6892, -74.0444);
        Coordinates eiffelTower = Coordinates.locatedAt(48.8583, 2.2945);

        Length distance = statueOfLiberty.getDistanceTo(eiffelTower);

        assertThat(distance.toKilometers()).isWithin(0.5).of(5_837);
    }

    @Test
    public void givenTwoVeryNearLocationsAndVeryShortRange_ShouldBeInRange() {
        Coordinates googlePlex = Coordinates.locatedAt(37.4218047, -122.0838097);
        Coordinates googleSoccerField = Coordinates.locatedAt(37.424242, -122.0874509);
        Length range = Length.fromMiles(0.30);

        boolean inRange = googlePlex.isInRange(googleSoccerField, range);

        assertThat(inRange).isTrue();
    }

    @Test
    public void givenTwoFarLocationsButTooShortRange_ShouldBeInRange() {
        Coordinates manhattan = Coordinates.locatedAt(40.7791547, -73.9654464);
        Coordinates harvardUniversity = Coordinates.locatedAt(42.3770068, -71.1188488);
        Length range = Length.fromMiles(50);

        boolean inRange = manhattan.isInRange(harvardUniversity, range);

        assertThat(inRange).isFalse();
    }

    @Test
    public void givenTwoFarLocationsButVeryLargeRange_ShouldBeInRange() {
        Coordinates manhattan = Coordinates.locatedAt(40.7791547, -73.9654464);
        Coordinates harvardUniversity = Coordinates.locatedAt(42.3770068, -71.1188488);
        Length range = Length.fromMiles(200);

        boolean inRange = harvardUniversity.isInRange(manhattan, range);

        assertThat(inRange).isTrue();
    }

}
