package com.example.service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@Transactional
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    /**
     * Adds an account to the repository if and only if:
     * - the username is not blank
     * - the password has a length greater than or equal to 4
     * @param account the account to add to the repository.
     * @returns The account saved to the repository.
     */
    public Account addAccount(Account account){
        if(account.getUsername().isBlank()) return null;
        if(account.getPassword().length() < 4) return null;
        if(accountRepository.getAccount(account.getUsername()) != null) return null;

        return accountRepository.save(account);
    }

    /**
     * Attempts to get an account from the repository, by username.
     * @param the username to search by.
     * @returns The account found, or null if none were found.
     */
    public Account getAccountByUsername(String username){
        return accountRepository.getAccount(username);
    }

    /**
     * Attempts to get an account from the repository, by id.
     * @param the id to search by.
     * @returns The account found, or null if none were found.
     */
    public Account getAccountById(Integer id){
        Optional<Account> optionalAccount = accountRepository.findById(id);
        return optionalAccount.isPresent() ? optionalAccount.get() : null;
    }
    /**
     * Attempts to log into an account in the repository.
     * @param account the account to try to log into.
     * @returns The account logged into. Returns null if no matching account found.
     */
    public Account loginAccount(Account account){
        return accountRepository.getAccount(account.getUsername(), account.getPassword());
    }
}
