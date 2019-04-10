package com.educ_nc_spring_19.chat_engine_service.model.repository;

import com.educ_nc_spring_19.chat_engine_service.model.entity.Chat;
import com.educ_nc_spring_19.chat_engine_service.model.entity.Member;
import com.educ_nc_spring_19.chat_engine_service.model.entity.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface MessageRepository extends CrudRepository<Message, Long> {
    ArrayList<Message> findByOwnerId(long oid);

    ArrayList<Message> findById(long id);

    ArrayList<Message> findByChat(Chat chat);
}
