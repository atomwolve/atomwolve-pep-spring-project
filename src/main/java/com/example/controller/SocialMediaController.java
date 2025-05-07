package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.*;
import com.example.service.*;

@RestController
public class SocialMediaController 
{
    @Autowired
    AccountService accountService;
    @Autowired
    MessageService messageService;

    /**
    * /register post endpoint mapping, for account registration.
    * Attempts to register an account, succeeds if and only if:
    * - username does not already belong to an existing account
    * - account creation did not fail in the service layer
    * @param account the account being registered.
    * @returns If successful, returns the addedAccount (including its id).
    *          Upon failure, either returns:
*              - status code 409 (if duplicate)
    *          - status code 400 (general failure)
    */
    @PostMapping("/register")
    public ResponseEntity<Account> postRegister(@RequestBody Account account)
    {
        if(accountService.getAccountByUsername(account.getUsername()) != null)
        {//if an account with the desired username already exists, fail
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        
        var addedAccount = accountService.addAccount(account);  //Attempt to add the account

        if(addedAccount == null)
        {//if the service layer failed to add an account, fail
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(addedAccount, HttpStatus.OK);   //return the new account
    }

    /**
    * /login post endpoint mapping, for account login.
    * Attempts to log into an account, succeeds if and only if:
    * - account login did not fail in the service layer
    * @param account the account being logged into.
    * @returns If successful, returns the account logged into.
    *          Upon failure, returns status code 401 (unauthorized).
    */
    @PostMapping("/login")
    public ResponseEntity<Account> postLogin(@RequestBody Account account)
    {
        var loggedInAccount = accountService.loginAccount(account); //attempt to log into an account
        if(loggedInAccount == null)
        {//if the login failed in the service layer, fail
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(loggedInAccount, HttpStatus.OK); //return the logged-in account
    }

    /**
    * /messages get endpoint mapping, for retrieving all messages.
    * @returns all saved messages.
    */
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages()
    {
        return new ResponseEntity<>(messageService.getAllMessages(), HttpStatus.OK);
    }

    /**
    * /messages post endpoint mapping, for posting a message.
    * Attempts to post a message, succeeds if and only if:
    * - the message's postedBy field represents a user that exists
    * - message creation did not fail in the service layer
    * @param message the message being posted.
    * @returns If successful, returns the addedMessage (including its id).
    *          Upon failure, either returns status code 400 (bad request).
    */
    @PostMapping("/messages")
    public ResponseEntity<Message> postMessages(@RequestBody Message message)
    {
        if(accountService.getAccountById(message.getPostedBy()) == null)
        {//if the message's desired account doesn't exist, fail
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        var addedMessage = messageService.addMessage(message);  //attempt to add the message
        
        if(addedMessage == null)
        {//if the service layer failed to add the message, fail
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(addedMessage, HttpStatus.OK);   //return the added message
    }

    /**
    * /messages/{messageId} get endpoint mapping, for getting a specific message by its messageId.
    * @param messageId the message being searched for.
    * @returns the message found, or null if none were found.
    */
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId)
    {
        return new ResponseEntity<>(messageService.getMessageById(messageId), HttpStatus.OK);
    }

    /**
    * /accounts/{accountId}/messages get endpoint mapping, for retrieving messages by accountId.
    * @param account the account being registered.
    * @returns all messages posted by a corresponding accountId, if any.
    */
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByUserId(@PathVariable Integer accountId)
    {
        return new ResponseEntity<>(messageService.getMessagesByUser(accountId), HttpStatus.OK);
    }

    /**
    * /messages/{messageId} delete endpoint mapping, for message deletion.
    * @param messageId the id of the message being deleted.
    * @returns the status code 200, as well as a body of 1 if a message was deleted, or an empty string otherwise.
    */
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<String> deleteMessageById(@PathVariable Integer messageId)
    {
        return new ResponseEntity<>(messageService.deleteMessageById(messageId) ? "1" : "", HttpStatus.OK);
    }

    /**
    * /messages/{messageId} patch endpoint mapping, for updating a message.
    * @param messageId the id of the message being updated.
    * @param replacement the replacement message.
    * @returns If successful, returns the number of rows updated and the status code 200.
    *          Upon failure, returns status code 400 (bad request).
    */
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<String> patchMessageById(@PathVariable Integer messageId, @RequestBody Message replacement)
    {
        boolean success = messageService.patchMessageById(replacement, messageId);  //attempt to update the message
        if(success)
        {//if service layer succeeded, return success
            return new ResponseEntity<>("1", HttpStatus.OK);
        }

        //otherwise fail
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
