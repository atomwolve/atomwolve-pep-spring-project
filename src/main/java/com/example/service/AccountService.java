package com.example.service;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    /**
     * @param account the account to add to the repository.
     * @returns The account saved to the repository.
     */
    public Account addAccount(Account account){
        if(account.getUsername().isBlank()) return null;
        if(account.getPassword().length() < 4) return null;
        return accountRepository.save(account);
    }

    /**
     * @param account the account to try to log into.
     * @returns The account logged into. Returns null if no account found.
     */
    public Account loginAccount(Account account){
        return accountRepository.getAccount(account.getUsername(), account.getPassword());
    }
}
