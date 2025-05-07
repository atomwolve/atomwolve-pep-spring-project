package com.example.service;
import com.example.entity.Message;
import com.example.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MessageService
{
    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    /**
    * Adds a message to the message repository if and only if:
    * - the message text is not blank
    * - the message text is less than or equal to 255 characters in length
    * @param message the message to add to the repository.
    * @returns The message saved to the repository. Returns null if it fails.
    */
    public Message addMessage(Message message)
    {
        if(message.getMessageText().isBlank()) return null;
        if(message.getMessageText().length() > 255) return null;
        return messageRepository.save(message);
    }

    /**
    * Gets all saved messages.
    * @returns All messages saved to the repository.
    */
    public List<Message> getAllMessages()
    {
        return messageRepository.findAll();
    }

    /**
    * Searches for a message with an id of messageId.
    * @param messageId the messageId to search the repository by.
    * @returns The message found by its corresponding messageId. Returns null if it can't be found.
    */
    public Message getMessageById(Integer messageId)
    {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        return optionalMessage.isPresent() ? optionalMessage.get() : null;
    }

    /**
    * Gets all messages posted by a specific user with id userId.
    * @param userId the userId to search the repository by.
    * @returns All messages found by its corresponding userId.
    */
    public List<Message> getMessagesByUser(Integer userId)
    {
        return messageRepository.getMessagesByUserId(userId);
    }

    /**
    * Deletes a message with an id of messageId.
    * @param messageId the id of the message to be deleted. 
    * @returns Whether a message was found or not (and therefore whether a message was deleted or not).
    */
    public boolean deleteMessageById(Integer messageId)
    {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if(!optionalMessage.isPresent()) return false;
        messageRepository.deleteById(messageId);
        return true;
    }

    /**
    * Attempts to update an existing message, if and only if:
    * - the message text is not blank
    * - the message text is less than or equal to 255 characters in length
    * @param replacement the updated message to patch into the repository.
    * @param messageId the intended id of the original message.
    * @returns Whether a message was found or not (and therefore whether a message was patched or not).
    */
    public boolean patchMessageById(Message replacement, Integer messageId)
    {
        if(replacement.getMessageText().isBlank()) return false;
        if(replacement.getMessageText().length() > 255) return false;

        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if(optionalMessage.isPresent()){
            Message message = optionalMessage.get();
            message.setMessageText(replacement.getMessageText());
            messageRepository.save(message);
            return true;
        }
        return false;
    }
}
