package com.substring.chat.controllers;

import com.substring.chat.entities.Message;
import com.substring.chat.entities.Room;
import com.substring.chat.enums.MessageStatus;
import com.substring.chat.playload.MessageRequest;
import com.substring.chat.repositories.MessageRepository;
import com.substring.chat.repositories.RoomRepository;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/app")
public class ChatController {

    private final RoomRepository roomRepository;
    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(RoomRepository roomRepository,
                          MessageRepository messageRepository,
                          SimpMessagingTemplate messagingTemplate) {
        this.roomRepository = roomRepository;
        this.messageRepository = messageRepository;
        this.messagingTemplate = messagingTemplate;
    }

    // --------------------
    // SEND MESSAGE TO ROOM
    // --------------------
    @MessageMapping("/sendMessage/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public Message sendMessage(
            @DestinationVariable String roomId,
            MessageRequest request
    ) {

        Room room = roomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        Message message = new Message();
        message.setRoomId(room.getRoomId());   // BUSINESS roomId
        message.setSender(request.getSender());
        message.setContent(request.getContent());
        message.setStatus(MessageStatus.SENT);
        message.setDeleted(false);
        message.setTimeStamp(LocalDateTime.now());
        message.setUpdatedAt(LocalDateTime.now());

        messageRepository.save(message);
        return message;
    }

    // --------------------
    // ADMIN BROADCAST
    // --------------------
    @MessageMapping("/admin/broadcast")
    public void broadcast(MessageRequest request) {

        Message message = new Message();
        message.setRoomId("BROADCAST");
        message.setSender(request.getSender()); // ADMIN
        message.setContent(request.getContent());
        message.setStatus(MessageStatus.SENT);
        message.setTimeStamp(LocalDateTime.now());
        message.setUpdatedAt(LocalDateTime.now());

        messageRepository.save(message);

        messagingTemplate.convertAndSend(
                "/topic/admin-broadcast",
                message
        );
    }

    // --------------------
    // PRIVATE MESSAGE
    // --------------------
    @MessageMapping("/chat.private")
    public void privateMessage(MessageRequest request) {

        Message message = new Message();
        message.setSender(request.getSender());
        message.setContent(request.getContent());
        message.setStatus(MessageStatus.SENT);
        message.setTimeStamp(LocalDateTime.now());
        message.setUpdatedAt(LocalDateTime.now());

        messagingTemplate.convertAndSendToUser(
                request.getReceiver(),
                "/queue/messages",
                message
        );
    }
}
