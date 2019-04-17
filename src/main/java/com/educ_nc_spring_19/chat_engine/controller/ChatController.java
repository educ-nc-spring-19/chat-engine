package com.educ_nc_spring_19.chat_engine.controller;

import com.educ_nc_spring_19.chat_engine.model.entity.Chat;
import com.educ_nc_spring_19.chat_engine.model.entity.Member;
import com.educ_nc_spring_19.chat_engine.service.ChatServise;
import com.educ_nc_spring_19.chat_engine.service.MemberServise;
import com.educ_nc_spring_19.chat_engine.service.repo.ChatRepository;
import com.educ_nc_spring_19.chat_engine.service.repo.MemberRepository;
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
    @Autowired
    private ChatServise chatServise;
    @Autowired
    private MemberServise memberServise;

    @RequestMapping(value = "/chat/findById", method = RequestMethod.GET, produces = "application/json")
    public Chat getChatById(@RequestParam("id") UUID id) {
        if (chatRepository.existsById(id)) {
            return chatRepository.findById(id).get(0);
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/chat/findByOwnerId", method = RequestMethod.GET, produces = "application/json")
    public Chat getChatByOwnerId(@RequestParam("id") UUID id) {
        if (chatRepository.existsByHostId(id)) {
            return chatRepository.findByHostId(id).get(0);
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/chat/existsById", method = RequestMethod.GET, produces = "application/json")
    public boolean existsChatById(@RequestParam("id") UUID id) {
        return chatRepository.existsById(id);
    }

    @RequestMapping(value = "/chat/existsByOwerId", method = RequestMethod.GET, produces = "application/json")
    public boolean existsChatByOwnerId(@RequestParam("id") UUID id) {
        return chatRepository.existsByHostId(id);
    }

    @RequestMapping(value = "/chat/all", method = RequestMethod.GET, produces = "application/json")
    public ArrayList<Chat> getAllChat() {
        ArrayList<Chat> result = new ArrayList<>();
        Iterable<Chat> allChats = chatRepository.findAll();
        for (Chat c : allChats) {
            result.add(c);
        }
        return result;
    }

    @RequestMapping(value = "/chat/create", method = RequestMethod.GET, produces = "application/json")
    public String addChat(@RequestParam("owner_id") UUID oid, @RequestParam("user_id") UUID uid) {
        String result = "success";
        try {
            memberServise.saveMember(uid, chatServise.saveChat(oid));
        } catch (Exception e) {
            result = "Exception: " + e.getMessage();
        }
        return result;
    }

    //лучше юзать метод /app/chat.addUser из WebSocketController
    @RequestMapping(value = "/chat/openByChatId", method = RequestMethod.GET, produces = "application/json")
    public Chat openChatById(@RequestParam("user_id") UUID uid, @RequestParam("chat_id") UUID cid) {
        try {
            if (chatRepository.existsById(cid)) {
                Chat chat = chatRepository.findById(cid).get(0);
                //тут должен быть рест апи, может ли юзер открыть этот чат (котёл может только сотрудник котла)
                if (!memberRepository.existsByChatAndUserId(chat, uid)) {
                    memberServise.saveMember(uid, chat);
                }
                return chat;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    //лучше юзать метод /app/chat.addUser из WebSocketController
    @RequestMapping(value = "/chat/openByHost", method = RequestMethod.GET, produces = "application/json")
    public Chat openChatByHost(@RequestParam("host_id") UUID hid) {
        try {
            if (chatRepository.existsByHostId(hid)) {
                return chatRepository.findByHostId(hid).get(0);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @RequestMapping(value = "/chat/members", method = RequestMethod.GET, produces = "application/json")
    public ArrayList<Member> getChatMembers(@RequestParam("chat_id") UUID cid) {
        ArrayList<Member> result = new ArrayList<>();
        if (chatRepository.existsById(cid)) {
            Chat chat = chatRepository.findById(cid).get(0);
            for (Member m : chat.getMembers()) {
                if (m.getDateLeft() == null || m.getDateJoin().isAfter(m.getDateLeft())) {
                    result.add(m);
                }
            }
        }
        return result;
    }
}
