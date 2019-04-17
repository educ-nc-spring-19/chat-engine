package com.educ_nc_spring_19.chat_engine.service;

import com.educ_nc_spring_19.chat_engine.model.entity.Chat;
import com.educ_nc_spring_19.chat_engine.model.entity.Message;
import com.educ_nc_spring_19.chat_engine.service.repo.ChatRepository;
import com.educ_nc_spring_19.chat_engine.service.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MessageServise {
    @Autowired
    private MessageRepository messageRepository;

    public Message saveMessage(String type, Chat chat, UUID uid, String text) {
        try {
            Message message = new Message(type, chat, uid, text);
            messageRepository.save(message);
            return message;
        } catch (Exception e) {
            return null;
        }
    }

    public Message editMessage(UUID chatId, UUID messageId, String text) {
        Message message = messageRepository.findById(messageId).get(0);
        message.setText(text);
        message.setEdited(true);
        messageRepository.save(message);
        return message;
    }
}