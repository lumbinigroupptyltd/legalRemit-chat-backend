package com.substring.chat.entities;

import com.substring.chat.enums.RoomType;
import jakarta.persistence.*; // Correct package for JPA annotations
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String roomId; // Your custom business ID (e.g., "science-group")

    @Enumerated(EnumType.STRING)
    private RoomType type; // PRIVATE, GROUP

//    @ElementCollection(fetch = FetchType.EAGER)
//    @CollectionTable(name = "room_messages",
//            joinColumns = @JoinColumn(name = "room_internal_id")
//    )
//    private List<Message> messages = new ArrayList<>();
}