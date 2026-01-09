package com.substring.chat.controllers;

import com.substring.chat.entities.Message;
import com.substring.chat.playload.MessageUpdateRequest;
import com.substring.chat.services.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;


    @PutMapping("/update/{messageId}")
    public ResponseEntity<Message> updateMessage(@PathVariable String messageId, @RequestBody MessageUpdateRequest messageUpdateRequest) {

        return ResponseEntity
                .ok(messageService.edit(messageId,messageUpdateRequest));

    }

    @DeleteMapping("delete/{messageId}")
    public ResponseEntity<String> deleteMessage(@PathVariable String messageId){

        return ResponseEntity.ok(messageService.delete(messageId));
    }

}
