package com.example.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{

     /**
     * @param 
     * @returns 
     */
    @Query("FROM Account WHERE username = :username")
    Account getAccount(@Param("username") String username);

    @Query("FROM Account WHERE username = :username AND password = :password")
    Account getAccount(@Param("username") String username, @Param("password") String password);
}
