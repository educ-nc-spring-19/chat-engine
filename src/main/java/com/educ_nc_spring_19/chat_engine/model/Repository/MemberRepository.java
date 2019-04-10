package com.educ_nc_spring_19.chat_engine.model.Repository;

import com.educ_nc_spring_19.chat_engine.model.Entity.Chat;
import com.educ_nc_spring_19.chat_engine.model.Entity.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.UUID;

public interface MemberRepository extends CrudRepository<Member, Long> {
    ArrayList<Member> findByUserId(UUID uid);

    ArrayList<Member> findById(UUID id);

    ArrayList<Member> findByChat(Chat chat);

    boolean existsById(UUID id);

    boolean existsByChatAndUserId(Chat chat, UUID uuid);
}