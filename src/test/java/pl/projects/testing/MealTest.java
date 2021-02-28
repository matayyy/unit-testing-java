package pl.projects.testing;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class MealTest {

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
}
