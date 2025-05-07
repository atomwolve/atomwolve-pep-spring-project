package com.example.service;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MessageService {

    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    /**
     * @param message the message to add to the repository.
     * @returns The message saved to the repository.
     */
    public Message addMessage(Message message){
        if(message.getMessageText().isBlank()) return null;
        if(message.getMessageText().length() > 255) return null;
        return messageRepository.save(message);
    }

    /**
     * @returns All messages saved to the repository.
     */
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    /**
     * @param messageId the messageId to search the repository by.
     * @returns The message found by its corresponding messageId.
     */
    public Message getMessageById(Integer messageId){
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        return optionalMessage.isPresent() ? optionalMessage.get() : null;
    }

    /**
     * @param messageId the id of the message to be deleted. 
     * @returns Whether a message was found or not (and therefore whether a message was deleted or not).
     */
    public boolean deleteMessageById(Integer messageId){
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if(!optionalMessage.isPresent()) return false;
        messageRepository.deleteById(messageId);
        return true;
    }

    /**
     * @param message the updated message to patch into the repository.
     * @returns Whether a message was found or not (and therefore whether a message was patched or not).
     */
    public boolean patchMessageById(Message replacement, Integer messageId){

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

    /**
     * @param userId the userId to search the repository by.
     * @returns All messages found by its corresponding userId.
     */
    public List<Message> getMessagesByUser(Integer userId){
        return messageRepository.getMessagesByUserId(userId);
    }
}
