package com.example.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>
{
    /**
    * Searches the message database for all messages posted by an id userId.
    * @param userId the userId to search by.
    * @returns All messages where postedBy matches the provided userId.
    */
    @Query("FROM Message WHERE postedBy = :userId")
    List<Message> getMessagesByUserId(@Param("userId") Integer userId);
}
