package pl.projects.testing.exercise;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UnitServiceTest {

    @Mock
    private UnitRepository unitRepository;

    @Mock
    private CargoRepository cargoRepository;

    @InjectMocks
    private UnitService unitService;

    @Test
    void addedCargoShouldBeLoadedOnUnit() {
        Unit unit = new Unit(new Coordinates(0,0), 100, 100);
        Cargo cargo = new Cargo("Books", 10);
        when(cargoRepository.findCargoByName("Books")).thenReturn(Optional.of(cargo));

        unitService.addCargoByName(unit, "Books");

        verify(cargoRepository).findCargoByName("Books");
        assertEquals(10, unit.getLoad());
        assertEquals(cargo, unit.getCargo().get(0));
    }

    @Test
    void shouldThrowExceptionWhenCargoIsNotFoundToAdd() {
        Unit unit = new Unit(new Coordinates(0, 0), 100, 100);
        when(cargoRepository.findCargoByName("Books")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> unitService.addCargoByName(unit, "Books"));
    }

    @Test
    void shouldReturnUnitByCoordinates() {
        Coordinates coordinates = new Coordinates(100, 100);
        Unit unit = new Unit(coordinates,100, 100);
        when(unitRepository.getUnitByCoordinates(coordinates)).thenReturn(unit);

        Unit result = unitService.getUnitOn(coordinates);

        assertSame(unit, result);
    }

    @Test
    void shouldReturnExceptionWhenUnitNotFound() {
        when(unitRepository.getUnitByCoordinates(new Coordinates(0,0))).thenReturn(null);

        assertThrows(NoSuchElementException.class, () -> unitService.getUnitOn(new Coordinates(0,0)));
    }
}
