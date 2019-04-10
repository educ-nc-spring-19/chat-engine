package com.educ_nc_spring_19.chat_engine.service.repo;

import com.educ_nc_spring_19.chat_engine.model.entity.Chat;
import com.educ_nc_spring_19.chat_engine.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Date;

import java.util.ArrayList;

public interface MessageJPARepository extends JpaRepository<Message, Integer> {
    @Query(value = "SELECT * FROM Message m WHERE m.dateSending < :dateSending and m.chat = :chat order by m.dateSending",
            nativeQuery = true)
    ArrayList<Message> findMessagesByDateSendingAndChat(@Param("dateSending") Date date, @Param("chat") Chat chat);
}