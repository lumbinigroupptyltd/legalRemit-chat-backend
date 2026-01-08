package com.substring.chat.repositories;


import com.substring.chat.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {

    //get room using room id
    Optional<Room> findByRoomId(String roomId);
}
