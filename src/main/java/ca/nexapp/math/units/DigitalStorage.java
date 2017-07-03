package ca.nexapp.math.units;

import java.util.Objects;

public class DigitalStorage implements Comparable<DigitalStorage> {

    private static final long BITS_IN_A_BYTE = 8;
    private static final long BITS_IN_A_KILOBYTE = BITS_IN_A_BYTE * 1024;
    private static final long BITS_IN_A_MEGABYTE = BITS_IN_A_KILOBYTE * 1024;
    private static final long BITS_IN_A_GIGABYTE = BITS_IN_A_MEGABYTE * 1024;
    private static final long BITS_IN_A_TERABYTE = BITS_IN_A_GIGABYTE * 1024;
    private static final long BITS_IN_A_PETABYTE = BITS_IN_A_TERABYTE * 1024;

    private long bits;

    private DigitalStorage(long bits) {
        this.bits = bits;
    }

    public DigitalStorage add(DigitalStorage augend) {
        return new DigitalStorage(bits + augend.bits);
    }

    public DigitalStorage subtract(DigitalStorage subtrahend) {
        return new DigitalStorage(bits - subtrahend.bits);
    }

    public long toBits() {
        return bits;
    }

    public long toBytes() {
        return bits / BITS_IN_A_BYTE;
    }

    public long toKilobytes() {
        return bits / BITS_IN_A_KILOBYTE;
    }

    public long toMegabytes() {
        return bits / BITS_IN_A_MEGABYTE;
    }

    public long toGigabytes() {
        return bits / BITS_IN_A_GIGABYTE;
    }

    public long toTerabytes() {
        return bits / BITS_IN_A_TERABYTE;
    }

    public long toPetabytes() {
        return bits / BITS_IN_A_PETABYTE;
    }

    public static DigitalStorage bits(long bits) {
        return new DigitalStorage(bits);
    }

    public static DigitalStorage bytes(long bytes) {
        return new DigitalStorage(bytes * BITS_IN_A_BYTE);
    }

    public static DigitalStorage kilobytes(long kilobytes) {
        return new DigitalStorage(kilobytes * BITS_IN_A_KILOBYTE);
    }

    public static DigitalStorage megabytes(long megabytes) {
        return new DigitalStorage(megabytes * BITS_IN_A_MEGABYTE);
    }

    public static DigitalStorage gigabytes(long gigabytes) {
        return new DigitalStorage(gigabytes * BITS_IN_A_GIGABYTE);
    }

    public static DigitalStorage terabytes(long terabytes) {
        return new DigitalStorage(terabytes * BITS_IN_A_TERABYTE);
    }

    public static DigitalStorage petabytes(long petabytes) {
        return new DigitalStorage(petabytes * BITS_IN_A_PETABYTE);
    }

    @Override
    public int compareTo(DigitalStorage o) {
        return Long.compare(bits, o.bits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bits);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DigitalStorage)) {
            return false;
        }

        DigitalStorage other = (DigitalStorage) obj;
        return Objects.equals(bits, other.bits);
    }

    @Override
    public String toString() {
        return bits + " bits";
    }
}
