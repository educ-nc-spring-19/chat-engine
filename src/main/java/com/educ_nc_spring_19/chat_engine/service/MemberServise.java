package com.educ_nc_spring_19.chat_engine.service;

import com.educ_nc_spring_19.chat_engine.model.entity.Chat;
import com.educ_nc_spring_19.chat_engine.model.entity.Member;
import com.educ_nc_spring_19.chat_engine.service.repo.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MemberServise {
    @Autowired
    private MemberRepository memberRepository;

    public Member saveMember(UUID oid, Chat chat) {
        try {
            Member member = new Member(oid, chat);
            memberRepository.save(member);
            return member;
        } catch (Exception e) {
            return null;
        }
    }
}