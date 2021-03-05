package pl.projects.testing.exercise;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinatesTest {

    @Test
    void correctCoordinatesShouldBeCreated() {
        Coordinates coordinates = new Coordinates(12,55);

        assertEquals(12, coordinates.getX());
        assertEquals(55, coordinates.getY());
    }

    @Test
    void twoCoordinatesWithEqualsPositionShouldBeEquals() {
        Coordinates coordinates1 = new Coordinates(100, 0);
        Coordinates coordinates2 = new Coordinates(100, 0);

        assertEquals(coordinates1, coordinates2);
    }

    @Test
    void coordinatesShouldNotBeNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Coordinates(10, -10));
        assertThrows(IllegalArgumentException.class, () -> new Coordinates(-10, 10));
        assertThrows(IllegalArgumentException.class, () -> new Coordinates(-10,-10));
    }

    @Test
    void coordinatesShouldNotBeGreaterThan100 () {
        assertThrows(IllegalArgumentException.class, () -> new Coordinates(0, 101));
        assertThrows(IllegalArgumentException.class, () -> new Coordinates(101, 0));
        assertThrows(IllegalArgumentException.class, () -> new Coordinates(200, 200));
    }

    @Test
    void copingCoordinatesShouldChangePosition() {
        Coordinates coordinates = new Coordinates(10,60);

        Coordinates copy = Coordinates.copy(coordinates, 20, 20);

        assertEquals(30, copy.getX());
        assertEquals(80, copy.getY());
    }

    @Test
    void copyCoordinatesWithoutAddingAdditionalPositionShouldBeTheSameAsOriginal() {
        Coordinates coordinates = new Coordinates(10, 10);

        Coordinates copy = Coordinates.copy(coordinates, 0, 0);

        assertEquals(coordinates,copy);
        assertNotSame(coordinates, copy);
    }
}
