package com.educ_nc_spring_19.chat_engine.controller;

import com.educ_nc_spring_19.chat_engine.model.entity.Chat;
import com.educ_nc_spring_19.chat_engine.model.entity.Message;
import com.educ_nc_spring_19.chat_engine.service.repo.ChatRepository;
import com.educ_nc_spring_19.chat_engine.service.repo.MessageJPARepository;
import com.educ_nc_spring_19.chat_engine.service.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@RestController
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageJPARepository messageJPARepository;
    @Autowired
    private ChatRepository chatRepository;

    @RequestMapping(value = "/message/findById", method = RequestMethod.GET, produces = "application/json")
    public Message getMessageById(@RequestParam("id") UUID id) {
        if (messageRepository.existsById(id)) {
            return messageRepository.findById(id).get(0);
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/message/existsById", method = RequestMethod.GET, produces = "application/json")
    public boolean existsMessageById(@RequestParam("id") UUID id) {
        return messageRepository.existsById(id);
    }

    @RequestMapping(value = "/message/all", method = RequestMethod.GET, produces = "application/json")
    public ArrayList<Message> getAllMessages() {
        ArrayList<Message> result = new ArrayList<>();
        for (Message m : messageRepository.findAll()) {
            result.add(m);
        }
        return result;
    }

    @RequestMapping(value = "/message/findByOwnerId", method = RequestMethod.GET, produces = "application/json")
    public ArrayList<Message> getMessagesByOwnerId(@RequestParam("owner_id") UUID id) {
        return messageRepository.findByOwnerId(id);
    }

    @RequestMapping(value = "/message/findByChat", method = RequestMethod.GET, produces = "application/json")
    public ArrayList<Message> getMessagesByChat(@RequestParam("chat_id") UUID id) {
        if (chatRepository.existsById(id)) {
            Chat chat = chatRepository.findById(id).get(0);
            return messageRepository.findByChat(chat);
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/message/portion", method = RequestMethod.GET, produces = "application/json")
    public ArrayList<Message> getPortion(@RequestParam("date_sending") String datestr,
                                         @RequestParam("chat_id") UUID chatId, @RequestParam("id1") UUID id1,
                                         @RequestParam("id2") UUID id2, @RequestParam("id3") UUID id3,
                                         @RequestParam("id4") UUID id4, @RequestParam("id5") UUID id5) {
        try {
            if (chatRepository.existsById(chatId)) {
                Chat chat = chatRepository.findById(chatId).get(0);
                OffsetDateTime date = OffsetDateTime.parse(datestr);
                ArrayList<Message> allPreviousMessages = messageJPARepository.findMessagesByDateSendingAndChat_Id(date, chat.getId());
                ArrayList<Message> portion = new ArrayList<>();
                ArrayList<UUID> lastFiveIds = new ArrayList<>(Arrays.asList(id1, id2, id3, id4, id5));
                for (int i = 0; i < 30; i++) {
                    if (allPreviousMessages.size() > i && !lastFiveIds.contains(allPreviousMessages.get(i).getId())) {
                        portion.add(allPreviousMessages.get(i));
                    }
                }
                return portion;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @RequestMapping(value = "/message/send", method = RequestMethod.POST, produces = "application/json")
    public String sendMessage(@RequestParam("user_id") UUID uid, @RequestParam("chat_id") UUID cid,
                                                @RequestParam("text") String text) {
        String result = "success";
        try {
            Chat chat = chatRepository.findById(cid).get(0);
            Message message = new Message(chat, uid, text);
            messageRepository.save(message);
        } catch (Exception e) {
            result = "Exception: " + e.getMessage();
        }
        return result;
    }

    @RequestMapping(value = "/message/edit", method = RequestMethod.POST, produces = "application/json")
    public String editMessage(@RequestParam("message_id") UUID mid, @RequestParam("user_id") UUID uid,
                                    @RequestParam("text") String text) {
        String result = "success";
        try {
            Message message = messageRepository.findById(mid).get(0);
            if (message.getOwnerId().equals(uid)) {
                message.setEdited(true);
                message.setText(text);
                messageRepository.save(message);
            } else {
                result = "This user isn't editing message owner";
            }
        } catch (Exception e) {
            result = "Exception: " + e.getMessage();
        }
        return result;
    }

    @RequestMapping(value = "/message/delete", method = RequestMethod.POST, produces = "application/json")
    public String deleteMessage(@RequestParam("message_id") UUID mid, @RequestParam("user_id") UUID uid) {
        String result = "success";
        try {
            Message message = messageRepository.findById(mid).get(0);
            if (message.getOwnerId().equals(uid)) {
                messageRepository.delete(message);
            } else {
                result = "This user isn't editing message owner";
            }
        } catch (Exception e) {
            result = "Exception: " + e.getMessage();
        }
        return result;
    }
}
