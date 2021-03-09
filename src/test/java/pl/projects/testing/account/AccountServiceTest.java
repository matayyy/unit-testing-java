package pl.projects.testing.account;

import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class AccountServiceTest {

    @Test
    void getAllActiveAccounts() {
        List<Account> accounts = prepareAccountData();
        AccountRepository accountRepository = mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepository);
        when(accountRepository.getAllAccounts()).thenReturn(accounts);

        List<Account> accountList = accountService.getAllActiveAccounts();

        assertThat(accountList, hasSize(2));
    }

    @Test
    void getNoActiveAccounts() {
        List<Account> accounts = prepareAccountData();
        AccountRepository accountRepository = mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepository);
        when(accountRepository.getAllAccounts()).thenReturn(accounts);

        List<Account> accountList = accountService.getNoActiveAccount();

        assertThat(accountList, hasSize(1));
    }

    @Test
    void getAccountsByName() {
        List<Account> accounts = prepareAccountData();
        AccountRepository accountRepository = mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepository);
        when(accountRepository.getByName("Dawid")).thenReturn(Collections.singletonList("Nowak"));

        List<String> accountNames = accountService.findByName("Dawid");

        assertThat(accountNames, contains("Nowak"));
    }

    private List<Account> prepareAccountData() {
        Account account1 = new Account(new Address("Kwiatowa", "33/5"));
        Account account2 = new Account();
        Account account3 = new Account(new Address("Poniatowskiego", "32/6"));

        return Arrays.asList(account1, account2, account3);
    }
}
