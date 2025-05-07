package com.example.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.entity.Account;
import com.example.entity.Message;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

     /**
     * @param userId the userId to search by.
     * @returns All messages where postedBy matches the provided userId.
     */
    @Query("FROM Message WHERE postedBy = :userId")
    List<Message> getMessagesByUserId(@Param("userId") long userId);

    @Query("FROM Account WHERE username = :username AND password = :password")
    Account getAccount(@Param("username") String username, @Param("password") String password);
}
