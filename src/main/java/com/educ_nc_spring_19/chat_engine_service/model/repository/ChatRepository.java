package com.educ_nc_spring_19.chat_engine_service.model.repository;

import com.educ_nc_spring_19.chat_engine_service.model.entity.Chat;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ChatRepository extends CrudRepository<Chat, Long> {
    ArrayList<Chat> findById(long id);

    boolean existsById(long id);
}