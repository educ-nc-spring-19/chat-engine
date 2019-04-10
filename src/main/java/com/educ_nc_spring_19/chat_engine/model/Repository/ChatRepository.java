package com.educ_nc_spring_19.chat_engine.model.Repository;

import com.educ_nc_spring_19.chat_engine.model.Entity.Chat;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.UUID;

public interface ChatRepository extends CrudRepository<Chat, Long> {
    ArrayList<Chat> findById(UUID id);

    boolean existsById(UUID id);
}