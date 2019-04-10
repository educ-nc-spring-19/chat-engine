package com.educ_nc_spring_19.chat_engine_service.model.RestControllers;

import com.educ_nc_spring_19.chat_engine_service.model.entity.Chat;
import com.educ_nc_spring_19.chat_engine_service.model.entity.Message;
import com.educ_nc_spring_19.chat_engine_service.model.repository.ChatRepository;
import com.educ_nc_spring_19.chat_engine_service.model.repository.MessageJPARepository;
import com.educ_nc_spring_19.chat_engine_service.model.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@RestController
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageJPARepository messageJPARepository;
    @Autowired
    private ChatRepository chatRepository;

    @RequestMapping(value = "/message/findById", method = RequestMethod.GET, produces = "application/json")
    public Message getMessageById(@RequestParam("id") long id) {
        /*Logger logger = LoggerFactory.getLogger(CharacterController.class);
        Double result = characterRepository.findById(id).get(0).getPersMoney();
        logger.info(Double.toString(result)); */
        return messageRepository.findById(id).get(0);
    }

    @RequestMapping(value = "/message/findByOwnerId", method = RequestMethod.GET, produces = "application/json")
    public ArrayList<Message> getMessagesByOwnerId(@RequestParam("owner_id") long id) {
        return messageRepository.findByOwnerId(id);
    }

    @RequestMapping(value = "/message/findByChat", method = RequestMethod.GET, produces = "application/json")
    public ArrayList<Message> getMessagesByChat(@RequestParam("chat_id") long id) {
        Chat chat = chatRepository.findById(id).get(0);
        return messageRepository.findByChat(chat);
    }

    @RequestMapping(value = "/message/all", method = RequestMethod.GET, produces = "application/json")
    public ArrayList<Message> getAllMessages() {
        ArrayList<Message> result = new ArrayList<>();
        for (Message m : messageRepository.findAll()) {
            result.add(m);
        }
        return result;
    }

    @RequestMapping(value = "/message/portion", method = RequestMethod.GET, produces = "application/json")
    public ArrayList<Message> getPortion(@RequestParam("date_sending")Date date, @RequestParam("chat_id") long chatId,
                                         @RequestParam("id1") long id1,
                                         @RequestParam("id2") long id2, @RequestParam("id3") long id3,
                                         @RequestParam("id4") long id4, @RequestParam("id5") long id5) {
        Chat chat = chatRepository.findById(chatId).get(0);
        ArrayList<Message> allPreviousMessages = messageJPARepository.findMessagesByDateSendingAndChat(date, chat);
        ArrayList<Message> portion = new ArrayList<>();
        ArrayList<Long> lastFiveIds = new ArrayList<>(Arrays.asList(id1, id2, id3, id4, id5));
        for (int i = 0; i < 30; i++) {
            if (allPreviousMessages.size() >= i && !lastFiveIds.contains(allPreviousMessages.get(i).getId())) {
                portion.add(allPreviousMessages.get(i));
            }
        }
        return portion;
    }

    @RequestMapping(value = "/message/send", method = RequestMethod.POST, produces = "application/json")
    public String getMessagesByChat(@RequestParam("user_id") long uid, @RequestParam("chat_id") long cid,
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
}
