package pl.projects.testing.account;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    List<Account> getAllActiveAccounts() {
        return accountRepository.getAllAccounts().stream()
                .filter(Account::isActive)
                .collect(Collectors.toList());
    }

    List<Account> getNoActiveAccount() {
        return accountRepository.getAllAccounts().stream()
                .filter(Predicate.not(Account::isActive))
                .collect(Collectors.toList());
    }
}
