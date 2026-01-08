package com.substring.chat.repositories;

import com.substring.chat.entities.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {

    Page<Message> findByRoomIdOrderByTimeStampDesc(String roomId, Pageable pageable);

    Message findByRoomId(String roomId);

    List<Message> findListByRoomId(String roomId);
}
