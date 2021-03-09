package pl.projects.testing.account;

import java.util.Arrays;
import java.util.List;

public class AccountRepositoryStub implements AccountRepository {

    @Override
    public List<Account> getAllAccounts() {
        Account account1 = new Account(new Address("Kwiatowa", "33/5"));
        Account account2 = new Account();
        Account account3 = new Account(new Address("Poniatowskiego", "32/6"));

        return Arrays.asList(account1, account2, account3);
    }

    @Override
    public List<String> getByName(String name) {
        return null;
    }
}
