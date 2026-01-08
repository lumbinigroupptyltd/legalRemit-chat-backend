package com.substring.chat.controllers;

import com.substring.chat.entities.Message;
import com.substring.chat.entities.Room;
import com.substring.chat.repositories.MessageRepository;
import com.substring.chat.repositories.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
// @CrossOrigin(origins = "${ws.front.end.base.url}")
public class RoomController {

    private final RoomRepository roomRepository;
    private final MessageRepository messageRepository;

    public RoomController(RoomRepository roomRepository,
                          MessageRepository messageRepository) {
        this.roomRepository = roomRepository;
        this.messageRepository = messageRepository;
    }

    // --------------------
    // CREATE ROOM
    // --------------------
    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody String roomId) {

        if (roomRepository.findByRoomId(roomId).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body("Room already exists!");
        }

        Room room = new Room();
        room.setRoomId(roomId);

        Room savedRoom = roomRepository.save(room);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedRoom);
    }

    // --------------------
    // JOIN ROOM (SAFE)
    // --------------------
    @GetMapping("/{roomId}")
    public ResponseEntity<?> joinRoom(@PathVariable String roomId) {

        return roomRepository.findByRoomId(roomId)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.badRequest().build()
                );
    }



    // --------------------
    // GET ROOM MESSAGES (PAGINATED)
    // --------------------
    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<Message>> getMessages(
            @PathVariable String roomId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        if (roomRepository.findByRoomId(roomId).isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Page<Message> messagePage =
                messageRepository.findByRoomIdOrderByTimeStampDesc(
                        roomId,
                        PageRequest.of(page, size)
                );

        return ResponseEntity.ok(messagePage.getContent());
    }
}


