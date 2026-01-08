package com.substring.chat.services.message;


import com.substring.chat.entities.Message;
import com.substring.chat.playload.MessageUpdateRequest;

public interface MessageService {

    Message update(String messageId, MessageUpdateRequest updateRequest);




}
