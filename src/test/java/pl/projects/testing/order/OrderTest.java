package pl.projects.testing.order;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.projects.testing.Meal;
import pl.projects.testing.extensions.BeforeAfterExtension;
import pl.projects.testing.order.Order;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(BeforeAfterExtension.class)
class OrderTest {

    private Order order;

    @BeforeEach
    void initializeOrder() {
        System.out.println("Before each");
        order = new Order();
    }

    @AfterEach
    void cleanUp() {
        System.out.println("After each");
        order.cancel();
    }

    @Test
    void testAssertArraysEqual() {
        int[] ints1 = {1,2,3};
        int[] ints2 = {1,2,3};
        assertArrayEquals(ints1, ints2);
    }

    @Test
    void mealListShouldBeEmptyAfterCreationOfOrder() {
        assertThat(order.getMeals(), empty());
        assertThat(order.getMeals().size(), equalTo(0));
        assertThat(order.getMeals(), hasSize(0));
        MatcherAssert.assertThat(order.getMeals(), emptyCollectionOf(Meal.class));
    }

    @Test
    void addingMealToOrderShouldIncreaseOrderSize() {
        Meal meal = new Meal(15, "Burger");

        order.addMealToOrder(meal);
        assertThat(order.getMeals(), hasSize(1));
        assertThat(order.getMeals(), contains(meal));
        assertThat(order.getMeals(),hasItem(meal));

        assertThat(order.getMeals().get(0).getPrice(), equalTo(15));
    }

    @Test
    void removingMealFromOrderShouldDecreaseOrderSize() {
        Meal meal = new Meal(15,"Burger");

        order.addMealToOrder(meal);
        order.removeMealFromOrder(meal);

        assertThat(order.getMeals(), hasSize(0));
        assertThat(order.getMeals(), not(contains(meal)));
    }

    @Test
    void mealsShouldBeInCorrectOrderAfterAddingThemToOrder() {
        Meal meal = new Meal(15,"Burger");
        Meal meal1 = new Meal(10, "Pizza");

        order.addMealToOrder(meal);
        order.addMealToOrder(meal1);

        assertThat(order.getMeals(), contains(meal, meal1));
        assertThat(order.getMeals(), containsInAnyOrder(meal1, meal));
    }

    @Test
    void testIfTwoMealListAreTheSame() {
        Meal meal = new Meal(15,"Burger");
        Meal meal1 = new Meal(10, "Pizza");
        Meal meal2 = new Meal(30,"Fish");

        List<Meal> meals1 = Arrays.asList(meal,meal1);
        List<Meal> meals2 = Arrays.asList(meal,meal1);

        assertThat(meals1, is(meals2));
    }

    @Test
    void orderTotalPriceShouldNotExceedsMaxIntValue() {
        Meal meal1 = new Meal(Integer.MAX_VALUE, "Pizza");
        Meal meal2 = new Meal(30,"Fish");

        order.addMealToOrder(meal1);
        order.addMealToOrder(meal2);

        assertThrows(IllegalStateException.class, () -> order.totalPrice());
    }

    @Test
    void emptyOrderTotalPriceShouldEqualZero() {
        assertEquals(0, order.totalPrice());
    }

    @Test
    void cancelingOrderShouldRemoveAllItemsFromMealsList() {
        Meal meal1 = new Meal(300, "Pizza");
        Meal meal2 = new Meal(30,"Fish");

        order.addMealToOrder(meal1);
        order.addMealToOrder(meal2);
        order.cancel();

        assertThat(order.getMeals().size(), is(0));
    }

}
