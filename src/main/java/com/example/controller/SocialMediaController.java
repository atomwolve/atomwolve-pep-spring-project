package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.*;
import com.example.service.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    // @Autowired
    // AccountService accountService;
    // @Autowired
    // MessageService messageService;

    AccountService accountService;
    MessageService messageService;
    
    public SocialMediaController(){
        accountService = new AccountService();
        messageService = new MessageService();
    }

    @PostMapping(value = "/localhost:8080/register")
    public Account postRegister(@RequestBody Account account){
        return accountService.addAccount(account);
    }
    
    @PostMapping(value = "/localhost:8080/login")
    public Account postLogin(@RequestBody Account account){
        return accountService.loginAccount(account);
    }

    @GetMapping(value = "/localhost:8080/messages")
    public List<Message> postMessages(){
        return messageService.getAllMessages();
    }

    @GetMapping(value = "/localhost:8080/messages/{messageId}")
    public Message getMessageById(@PathVariable long messageId){
        return messageService.getMessageById(messageId);
    }

    @GetMapping(value = "/localhost:8080/accounts/{accountId}/messages")
    public List<Message> getMessagesByUserId(@PathVariable long accountId){
        return messageService.getMessagesByUser(accountId);
    }

    @DeleteMapping(value = "/localhost:8080/messages/{messageId}")
    public int deleteMessageById(@PathVariable long messageId){
        return messageService.deleteMessageById(messageId) ? 1 : 0;
    }

    @PatchMapping(value = "/localhost:8080/messages/{messageId}")
    public int patchMessageById(@PathVariable long messageId, @RequestBody Message message){
        return messageService.patchMessageById(message, messageId) ? 1 : 0;
    }
}
