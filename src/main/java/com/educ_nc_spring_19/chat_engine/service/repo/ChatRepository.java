package com.educ_nc_spring_19.chat_engine.service.repo;

import com.educ_nc_spring_19.chat_engine.model.entity.Chat;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.UUID;

public interface ChatRepository extends CrudRepository<Chat, Long> {
    ArrayList<Chat> findById(UUID id);

    ArrayList<Chat> findByHostId(UUID id);

    boolean existsById(UUID id);

    boolean existsByHostId(UUID id);
}