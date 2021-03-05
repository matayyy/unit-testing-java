package pl.projects.testing.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitTest {

    private Unit unit;

    @BeforeEach
    void initializeUnit () {
        unit = new Unit(new Coordinates(0, 50),100,100);
    }

    @Test
    void correctUnitShouldBeCreated() {
        assertEquals(100, unit.getFuel());
        assertEquals(0, unit.getLoad());
    }

    @Test
    void coordinatesShouldBeDifferentAfterMoving() {
        Coordinates move = unit.move(50,50);

        assertEquals(50, move.getX());
        assertEquals(100, move.getY());
    }

    @Test
    void coordinatesShouldBeSameAfterMovingByZeroStep() {
        Coordinates move = unit.move(0,0);

        assertEquals(0, move.getX());
        assertEquals(50, move.getY());
    }

    @Test
    void unitShouldNotMoveMoreThanMaxFuel() {
        assertThrows(IllegalStateException.class, () -> unit.move(90,20));
    }

    @Test
    void unitCannotRefuelingMoreThanMaxFuelCapacity() {
        unit.tankUp();

        assertEquals(100, unit.getFuel());
    }

    @Test
    void movingShouldUseFuel() {
        unit.move(10,10);

        assertEquals(80, unit.getFuel());
    }

    @Test
    void tankUpShouldChangeFuelLevel() {
        unit.move(20, 20);
        unit.tankUp();

        assertTrue(unit.getFuel() >= 50);
        assertTrue(unit.getFuel() < 60);
        //assertNotEquals(60, unit.getFuel());
    }

    @Test
    void loadingCargoShouldChangeCargoWeight() {
        Cargo cargo1 = new Cargo("Cheese", 10);
        Cargo cargo2 = new Cargo("Pizzas", 20);

        unit.loadCargo(cargo1);
        unit.loadCargo(cargo2);

        assertEquals(30, unit.getLoad());
    }

    @Test
    void loadingAboveCargoWeightShouldThrowError() {
        Cargo cargo = new Cargo("Above", 120);

        assertThrows(IllegalStateException.class, () -> unit.loadCargo(cargo));
    }

    @Test
    void unloadCargoShouldChangeCargoWeightToZero() {
        Cargo cargo1 = new Cargo("Cheese", 10);
        Cargo cargo2 = new Cargo("Pizzas", 20);

        unit.loadCargo(cargo1);
        unit.loadCargo(cargo2);
        unit.unloadAllCargo();

        assertEquals(0, unit.getLoad());
    }

}
