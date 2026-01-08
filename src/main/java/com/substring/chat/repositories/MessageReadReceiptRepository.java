package com.substring.chat.repositories;

import com.substring.chat.entities.MessageReadReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageReadReceiptRepository extends JpaRepository<MessageReadReceipt, Long> {

    boolean existsByMessageIdAndUserId(String messageId, String userId);
}
