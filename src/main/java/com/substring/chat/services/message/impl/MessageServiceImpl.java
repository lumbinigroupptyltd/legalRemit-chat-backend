package com.substring.chat.services.message.impl;

import com.substring.chat.entities.Message;
import com.substring.chat.playload.MessageUpdateRequest;
import com.substring.chat.repositories.MessageRepository;
import com.substring.chat.services.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;


    @Override
    public Message edit(String messageId, MessageUpdateRequest updateRequest) {

        // Find the existing message
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found with id: " + messageId));

        //Check if the message is flagged deleted or not
        if(message.isDeleted()){
            throw new RuntimeException("You cannot edit deleted message");
        }

        // Validate sender (optional, but recommended for security)
        if (!message.getSender().equals(updateRequest.getSender())) {
            throw new RuntimeException("Unauthorized: You can only update your own messages");
        }

        // Update message content
        message.setContent(updateRequest.getContent());
        message.setUpdatedAt(LocalDateTime.now()); // Assuming you have this field
        message.setEdited(true); // Flag to indicate message was edited

        // Save and return the updated message
        System.out.println(message);
        return messageRepository.save(message);
    }

    @Override
    public String delete(String messageId) {

        Message message= messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found with id: " + messageId));

        message.setDeleted(true);

        messageRepository.save(message);
        return "Message deleted successfully.";

    }

}
