package com.educ_nc_spring_19.chat_engine.model.RestControllers;

import com.educ_nc_spring_19.chat_engine.model.Entity.Chat;
import com.educ_nc_spring_19.chat_engine.model.Entity.Member;
import com.educ_nc_spring_19.chat_engine.model.Repository.ChatRepository;
import com.educ_nc_spring_19.chat_engine.model.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.UUID;

@RestController
public class ChatController {
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private MemberRepository memberRepository;

    @RequestMapping(value = "/chat/findById", method = RequestMethod.GET, produces = "application/json")
    public Chat getChatById(@RequestParam("id") UUID id) {
        /*Logger logger = LoggerFactory.getLogger(CharacterController.class);
        Double result = characterRepository.findById(id).get(0).getPersMoney();
        logger.info(Double.toString(result)); */
        return chatRepository.findById(id).get(0);
    }

    @RequestMapping(value = "/chat/existsById", method = RequestMethod.GET, produces = "application/json")
    public boolean existsChatById(@RequestParam("id") UUID id) {
        return chatRepository.existsById(id);
    }

    @RequestMapping(value = "/chat/all", method = RequestMethod.GET, produces = "application/json")
    public ArrayList<Chat> getAllChat() {
        ArrayList<Chat> result = new ArrayList<>();
        for (Chat c : chatRepository.findAll()) {
            result.add(c);
        }
        return result;
    }

    @RequestMapping(value = "/chat/create", method = RequestMethod.GET, produces = "application/json")
    public String addChat(@RequestParam("user_id") UUID uid) {
        String result = "success";
        try {
            Chat chat = new Chat();
            chatRepository.save(chat);
            Member member = new Member(uid, chat);
            memberRepository.save(member);
        } catch (Exception e) {
            result = "Exception: " + e.getMessage();
        }
        return result;
    }

    @RequestMapping(value = "/chat/open", method = RequestMethod.GET, produces = "application/json")
    public String addChat(@RequestParam("user_id") UUID uid, @RequestParam("chat_id") UUID cid) {
        String result = "success";
        try {
            Chat chat = chatRepository.findById(cid).get(0);
            //тут должен быть рест апи, может ли юзер открыть этот чат (котёл может только сотрудник котла)
            //и запрос, существует ли этот чат уже вообще (инфа хранится у юзера / котла / пула чат_id != null),
            // мб его делать придётся методом выше, но для этого мне надо будет залезть в md
            if (!memberRepository.existsByChatAndUserId(chat, uid)) {
                Member member = new Member(uid, chat);
                memberRepository.save(member);
            }
        } catch (Exception e) {
            result = "Exception: " + e.getMessage();
        }
        return result;
    }
}
