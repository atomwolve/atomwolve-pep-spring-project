package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @Autowired
    AccountService accountService;
    @Autowired
    MessageService messageService;

    @PostMapping("/register")
    public ResponseEntity<Account> postRegister(@RequestBody Account account){
        
        if(accountService.getAccountByUsername(account.getUsername()) != null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        
        var addedAccount = accountService.addAccount(account);
        
        if(addedAccount == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(addedAccount, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> postLogin(@RequestBody Account account){
        var loggedInAccount = accountService.loginAccount(account);
        if(loggedInAccount == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(loggedInAccount, HttpStatus.OK);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages(){
        return new ResponseEntity<>(messageService.getAllMessages(), HttpStatus.OK);
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> postMessages(@RequestBody Message message){
        
        if(accountService.getAccountById(message.getPostedBy()) == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        var addedMessage = messageService.addMessage(message);
        
        if(addedMessage == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(addedMessage, HttpStatus.OK);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId){
        var foundMessage = messageService.getMessageById(messageId);

        return new ResponseEntity<>(foundMessage, HttpStatus.OK);
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByUserId(@PathVariable Integer accountId){
        return new ResponseEntity<>(messageService.getMessagesByUser(accountId), HttpStatus.OK);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<String> deleteMessageById(@PathVariable Integer messageId){
        return new ResponseEntity<>(messageService.deleteMessageById(messageId) ? "1" : "", HttpStatus.OK);
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<String> patchMessageById(@PathVariable Integer messageId, @RequestBody Message message){
        boolean success = messageService.patchMessageById(message, messageId);
        if(success)        
            return new ResponseEntity<>("1", HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
