package ca.nexapp.math.units;

import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.util.Objects;

public class Coordinates {

    private static final Length MEAN_EARTH_RADIUS = Length.fromKilometers(6_371);

    private Angle latitude;
    private Angle longitude;

    private Coordinates(Angle latitude, Angle longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Angle getLatitude() {
        return latitude;
    }

    public Angle getLongitude() {
        return longitude;
    }

    public Length getDistanceTo(Coordinates destination) {
        double lat1 = latitude.toRadians();
        double lat2 = destination.latitude.toRadians();
        double lon1 = longitude.toRadians();
        double lon2 = destination.longitude.toRadians();
        double distance = acos(sin(lat1) * sin(lat2) + cos(lat1) * cos(lat2) * cos(lon1 - lon2)) * MEAN_EARTH_RADIUS.toKilometers();
        return Length.fromKilometers(distance);
    }

    public boolean isInRange(Coordinates location, Length range) {
        Length distance = getDistanceTo(location);
        return range.compareTo(distance) > 0;
    }

    public static Coordinates locatedAt(double latitude, double longitude) {
        return locatedAt(Angle.fromDegrees(latitude), Angle.fromDegrees(longitude));
    }

    public static Coordinates locatedAt(Angle latitude, Angle longitude) {
        return new Coordinates(latitude, longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(longitude, latitude);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Coordinates)) {
            return false;
        }
        Coordinates other = (Coordinates) obj;
        return Objects.equals(other.longitude, longitude) && Objects.equals(other.latitude, latitude);
    }

    @Override
    public String toString() {
        return latitude.toDegrees() + ", " + longitude.toDegrees();
    }

}
