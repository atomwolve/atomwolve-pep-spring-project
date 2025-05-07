package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{

    /**
     * Searches the account database for a user with a matching username.
    * @param username the username to search for.
    * @returns an account with a matching username.
    */
    @Query("FROM account WHERE username = :username")
    Account getAccount(@Param("username") String username);

    /**
      * Searches the account database for a user with a matching username and password.
     * @param username the username to search by.
     * @param password the password to search by.
     * @returns an account with a matching username and password.
     */
    @Query("FROM account WHERE username = :username AND password = :password")
    Account getAccount(@Param("username") String username, @Param("password") String password);
}
