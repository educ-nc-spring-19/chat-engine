package com.educ_nc_spring_19.chat_engine.service.repo;

import com.educ_nc_spring_19.chat_engine.model.entity.Chat;
import com.educ_nc_spring_19.chat_engine.model.entity.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.UUID;

public interface MessageRepository extends CrudRepository<Message, Long> {
    ArrayList<Message> findByOwnerId(UUID oid);

    ArrayList<Message> findById(UUID id);

    ArrayList<Message> findByChat(Chat chat);
}
