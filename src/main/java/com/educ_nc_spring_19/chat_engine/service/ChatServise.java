package com.educ_nc_spring_19.chat_engine.service;

import com.educ_nc_spring_19.chat_engine.model.entity.Chat;
import com.educ_nc_spring_19.chat_engine.service.repo.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChatServise {
    @Autowired
    private ChatRepository chatRepository;

    public Chat saveChat(UUID oid) {
        try {
            Chat chat = new Chat();
            chat.setHostId(oid);
            chatRepository.save(chat);
            return chat;
        } catch (Exception e) {
            return null;
        }
    }
}
