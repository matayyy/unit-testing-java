package pl.projects.testing;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@Tag("fries")
class AccountTest {

    @Test
    void newAccountShouldNotBeActiveAfterCreation() {
        Account newAccount = new Account();
        assertFalse(newAccount.isActive());
        assertThat(newAccount.isActive(), equalTo(false));
        assertThat(newAccount.isActive(), is(false));
    }

    @Test
    void accountShouldBeActiveAfterActivation() {
        Account newAccount = new Account();
        newAccount.activate();
        assertTrue(newAccount.isActive());
        assertThat(newAccount.isActive(), is(true));
    }

    @Test
    void newlyCreatedAccountShouldNotHaveDefaultDeliveryAddressSet() {
        Account account = new Account();
        Address address = account.getDefaultDeliveryAddress();
        assertNull(address);
        assertThat(address, nullValue());
    }

    @Test
    void defaultDeliveryAddressShouldNotBeNullAfterBeingSet() {
        Address address = new Address("Krakowska", "57c");
        Account account = new Account();
        account.setDefaultDeliveryAddress(address);
        Address defaultAddress = account.getDefaultDeliveryAddress();
        assertNotNull(defaultAddress);
        assertThat(defaultAddress, notNullValue());
    }

    @RepeatedTest(3)
    void newAccountWithNotNullAddressShouldBeActive() {
        Address address = new Address("Poniatowskiego", "45/5");

        Account account = new Account(address);
        assumingThat(address != null, () -> {
            assertTrue(account.isActive());
        });
    }

}
