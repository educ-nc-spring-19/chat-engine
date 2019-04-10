package com.educ_nc_spring_19.chat_engine.controller;

import com.educ_nc_spring_19.chat_engine.model.entity.Chat;
import com.educ_nc_spring_19.chat_engine.model.entity.Member;
import com.educ_nc_spring_19.chat_engine.service.repo.ChatRepository;
import com.educ_nc_spring_19.chat_engine.service.repo.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.UUID;

@RestController
public class MemberController {
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private MemberRepository memberRepository;

    @RequestMapping(value = "/member/findById", method = RequestMethod.GET, produces = "application/json")
    public Member getMemberById(@RequestParam("id") UUID id) {
        /*Logger logger = LoggerFactory.getLogger(CharacterController.class);
        Double result = characterRepository.findById(id).get(0).getPersMoney();
        logger.info(Double.toString(result)); */
        return memberRepository.findById(id).get(0);
    }

    @RequestMapping(value = "/member/findByUserId", method = RequestMethod.GET, produces = "application/json")
    public ArrayList<Member> getMemberByUserId(@RequestParam("id") UUID id) {
        return memberRepository.findByUserId(id);
    }

    @RequestMapping(value = "/member/findByChat", method = RequestMethod.GET, produces = "application/json")
    public ArrayList<Member> getMemberByChatId(@RequestParam("id") UUID id) {
        Chat chat = chatRepository.findById(id).get(0);
        return memberRepository.findByChat(chat);
    }

    @RequestMapping(value = "/member/add", method = RequestMethod.GET, produces = "application/json")
    public String addMember(@RequestParam("user_id") UUID uid, @RequestParam("chat_id") UUID cid) {
        String result = "success";
        try {
            Chat chat = chatRepository.findById(cid).get(0);
            Member member;
            if (memberRepository.existsById(uid)) {
                member = memberRepository.findById(uid).get(0);
                member.setDateJoin(OffsetDateTime.now());
            } else {
                member = new Member(uid, chat);
            }
            memberRepository.save(member);
        } catch (Exception e) {
            result = "Exception: " + e.getMessage();
        }
        return result;
    }

    @RequestMapping(value = "/member/remove", method = RequestMethod.GET, produces = "application/json")
    public String removeMember(@RequestParam("user_id") UUID uid, @RequestParam("chat_id") UUID cid) {
        String result = "success";
        try {
            Chat chat = chatRepository.findById(cid).get(0);
            Member member = new Member(uid, chat);
            member.setDateLeft(OffsetDateTime.now());
            memberRepository.save(member);
        } catch (Exception e) {
            result = "Exception: " + e.getMessage();
        }
        return result;
    }
}