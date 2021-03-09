package pl.projects.testing;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.projects.testing.extensions.IAExceptionIgnoreExtension;
import pl.projects.testing.order.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.endsWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MealTest {

    @Spy
    private Meal mealSpy;

    @Test
    void shouldReturnDiscountedPrice() {
        //given
        Meal meal = new Meal(45);

        //when
        int discountedPrice = meal.getDiscountedPrice(5);

        //then
        assertEquals(40, discountedPrice);
        assertThat(discountedPrice, equalTo(40));
    }

    @Test
    void referencesToTheSameObjectShouldBeEqual () {
        Meal meal1 = new Meal(10);
        Meal meal2 = meal1;

        assertSame(meal1, meal2);
        assertThat(meal1, sameInstance(meal2));
    }

    @Test
    void referencesToDifferentObjectShouldNotBeEqual() {
        Meal meal1 = new Meal(20);
        Meal meal2 = new Meal(20);

        assertNotSame(meal1,meal2);
        assertThat(meal1, not(sameInstance(meal2)));
    }

    @Test
    void twoMealsShouldBeEqualWhenNameAndPriceAreTheSame() {
        Meal meal1 = new Meal(10, "Pizza");
        Meal meal2 = new Meal(10, "Pizza");

        assertEquals(meal1, meal2);
    }

    @Test
    void exceptionShouldBeThrownIfDiscountIsHigherThenThePrice() {
        Meal meal = new Meal(10, "Pizza");

        assertThrows(IllegalArgumentException.class, () -> meal.getDiscountedPrice(15));
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 10, 15, 19})
    void mealPricesShouldBeLowerThan20(int price) {
        assertThat(price, lessThan(20));
    }

    @ParameterizedTest
    @MethodSource("createMealsWithNameAndPrice")
    void burgersShouldHaveCorrectNameAndPrice(String name, int price) {
        assertThat(name, containsString("burger"));
        assertThat(price, greaterThanOrEqualTo(10));
    }

    private static Stream<Arguments> createMealsWithNameAndPrice() {
        return Stream.of(
                Arguments.of("Hamburger", 10),
                Arguments.of("Cheeseburger", 15)
        );
    }

    @ParameterizedTest
    @MethodSource("createCakeNames")
    void cakeNamesShouldEndWithCake(String name) {
        assertThat(name, notNullValue());
        assertThat(name, endsWith("cake"));
    }

    private static Stream<String> createCakeNames() {
        List<String> cakeNames = Arrays.asList("Cheesecake", "Fruitcake", "Cupcake");
        return cakeNames.stream();
    }

    @ExtendWith(IAExceptionIgnoreExtension.class)
    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, 9})
    void mealPricesShouldBeLowerThan10(int price) {
        if(price > 5) {
            throw new IllegalArgumentException();
        }
        assertThat(price, lessThan(20));
    }

    @Tag("fries")
    @TestFactory
    Collection<DynamicTest> calculateMealPrices() {
        Order order = new Order();
        order.addMealToOrder(new Meal(10, "Hamburger", 2));
        order.addMealToOrder(new Meal(6, "Fries", 3));
        order.addMealToOrder(new Meal(22, "Pizza", 4));

        Collection<DynamicTest> dynamicTests = new ArrayList<>();

        for(int i =0; i<order.getMeals().size(); i++) {
            int price = order.getMeals().get(i).getPrice();
            int quantity = order.getMeals().get(i).getQuantity();

            Executable executable = () -> {
                assertThat(calculatePrice(price, quantity), lessThan(90));
            };

            String name = "Test name: " + i;
            DynamicTest dynamicTest = DynamicTest.dynamicTest(name, executable);
            dynamicTests.add(dynamicTest);
        }

        return dynamicTests;
    }

    @Test
    void testMealSumPrice() {
        Meal meal = mock(Meal.class);
        when(meal.getPrice()).thenReturn(15);
        when(meal.getQuantity()).thenReturn(3);
        when(meal.sumPrice()).thenCallRealMethod();

        int result = meal.sumPrice();

        assertThat(result, equalTo(45));
    }

    @Test
    @ExtendWith(MockitoExtension.class)
    void testMealSumPriceWitchSpy() {

//        Meal mealSpy = spy(Meal.class);
        when(mealSpy.getPrice()).thenReturn(15);
        when(mealSpy.getQuantity()).thenReturn(3);

        int result = mealSpy.sumPrice();

        verify(mealSpy).getPrice();
        verify(mealSpy).getQuantity();
        assertThat(result, equalTo(45));
    }

    private int calculatePrice(int price, int quantity) {
        return price * quantity;
    }
}
