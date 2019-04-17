package com.educ_nc_spring_19.chat_engine.controller;

import com.educ_nc_spring_19.chat_engine.model.entity.Chat;
import com.educ_nc_spring_19.chat_engine.model.entity.Message;
import com.educ_nc_spring_19.chat_engine.service.ChatServise;
import com.educ_nc_spring_19.chat_engine.service.MessageServise;
import com.educ_nc_spring_19.chat_engine.service.repo.ChatRepository;
import com.educ_nc_spring_19.chat_engine.service.repo.MemberRepository;
import com.educ_nc_spring_19.chat_engine.service.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class WebSocketController {
    @Autowired
    private ChatServise chatServise;
    @Autowired
    private MessageServise messageServise;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private MemberRepository memberRepository;

    @MessageMapping("/chat/{chatId}/sendMessage")
    @SendTo("/topic/{chatId}")
    public Message sendMessage(@DestinationVariable UUID chatId, @Payload UUID uid, @Payload String text) {
        return messageServise.saveMessage("CHAT", chatRepository.findById(chatId).get(0), uid, text);
    }

    @MessageMapping("/chat/{chatId}/editMessage")
    @SendTo("/topic/{chatId}")
    public Message editMessage(@DestinationVariable UUID chatId, @Payload UUID messageId,
                               @Payload String text) {
        Message message = messageServise.editMessage(chatId, messageId, text);
        return message;
    }

    //как это будет смотреться на фронте непонятно, да и как возвращать тоже
    @MessageMapping("/chat/{chatId}/deleteMessage")
    @SendTo("/topic/{chatId}")
    public Message deleteMessage(@DestinationVariable UUID chatId, @Payload UUID mid) {
        Message message = messageRepository.findById(mid).get(0);
        messageRepository.delete(message);
        message.setText("DELETED");
        return message;
    }

    @MessageMapping("/chat/{chatId}/addUser")
    @SendTo("/topic/{chatId}")
    public Message addUser(@DestinationVariable UUID chatId, @Payload UUID userId,
                           SimpMessageHeaderAccessor headerAccessor) {
        Message message = messageServise.saveMessage("JOIN", chatRepository.findById(chatId).get(0), userId, "");
        // Add userId in web socket session
        headerAccessor.getSessionAttributes().put("userId", message.getOwnerId());
        return message;
    }

    @MessageMapping("/chat/{chatId}/removeUser")
    @SendTo("/topic/{chatId}")
    public Message removeUser(@DestinationVariable UUID chatId, @Payload UUID userId,
                           SimpMessageHeaderAccessor headerAccessor) {
        Message message = messageServise.saveMessage("LEAVE", chatRepository.findById(chatId).get(0), userId, "");
        // Add username in web socket session
        headerAccessor.getSessionAttributes().remove("userId", message.getOwnerId());
        return message;
    }
}