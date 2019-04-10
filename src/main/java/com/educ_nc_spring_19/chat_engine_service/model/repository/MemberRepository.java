package com.educ_nc_spring_19.chat_engine_service.model.repository;

import com.educ_nc_spring_19.chat_engine_service.model.entity.Chat;
import com.educ_nc_spring_19.chat_engine_service.model.entity.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface MemberRepository extends CrudRepository<Member, Long> {
    ArrayList<Member> findByUserId(long uid);

    ArrayList<Member> findById(long id);

    ArrayList<Member> findByChat(Chat chat);
}