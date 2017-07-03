package ca.nexapp.math.units;

import static com.google.common.truth.Truth.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class DigitalStorageTest {

    @Test
    public void canConvertToBits() {
        DigitalStorage digital = DigitalStorage.bits(12);

        assertThat(digital.toBits()).isEqualTo(12);
    }

    @Test
    public void canConvertToBytes() {
        DigitalStorage digital = DigitalStorage.bits(24);

        assertThat(digital.toBytes()).isEqualTo(3);
    }

    @Test
    public void canConvertToKilobytes() {
        DigitalStorage digital = DigitalStorage.bytes(2048);

        assertThat(digital.toKilobytes()).isEqualTo(2);
    }

    @Test
    public void canConvertToMegabytes() {
        DigitalStorage digital = DigitalStorage.kilobytes(1024 * 8);

        assertThat(digital.toMegabytes()).isEqualTo(8);
    }

    @Test
    public void canConvertToGigabytes() {
        DigitalStorage digital = DigitalStorage.megabytes(1024 * 7);

        assertThat(digital.toGigabytes()).isEqualTo(7);
    }

    @Test
    public void canConvertToTerabytes() {
        DigitalStorage digital = DigitalStorage.gigabytes(1024 * 3);

        assertThat(digital.toTerabytes()).isEqualTo(3);
    }

    @Test
    public void canConvertToPetabytes() {
        DigitalStorage digital = DigitalStorage.terabytes(1024 * 12);

        assertThat(digital.toPetabytes()).isEqualTo(12);
    }

    @Test
    public void canAddTwoDigitalStorages() {
        DigitalStorage left = DigitalStorage.megabytes(2);
        DigitalStorage right = DigitalStorage.kilobytes(1055);

        DigitalStorage add = left.add(right);

        assertThat(add.toKilobytes()).isEqualTo(1055 + 2048);
    }

    @Test
    public void canSubtractTwoDigitalStorages() {
        DigitalStorage left = DigitalStorage.gigabytes(3);
        DigitalStorage right = DigitalStorage.megabytes(255);

        DigitalStorage subtract = left.subtract(right);

        assertThat(subtract.toMegabytes()).isEqualTo(1024 * 3 - 255);
    }

    @Test
    public void canCompareDigitalStorages() {
        DigitalStorage small = DigitalStorage.kilobytes(755);
        DigitalStorage medium = DigitalStorage.megabytes(12);
        DigitalStorage large = DigitalStorage.petabytes(1);

        List<DigitalStorage> storages = Stream.of(medium, large, small)
                .sorted()
                .collect(Collectors.toList());

        assertThat(storages).containsExactly(small, medium, large).inOrder();
    }
}
