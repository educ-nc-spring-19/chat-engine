package com.educ_nc_spring_19.chat_engine.service.repo;

import com.educ_nc_spring_19.chat_engine.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;

import java.util.ArrayList;
import java.util.UUID;

public interface MessageJPARepository extends JpaRepository<Message, Integer> {
    @Query(value = "SELECT * FROM Message m WHERE m.date_sending < :dateSending and m.chat_id = :chat_id order by m.date_sending",
            nativeQuery = true)
    ArrayList<Message> findMessagesByDateSendingAndChat_Id(@Param("dateSending") OffsetDateTime dateSending, @Param("chat_id") UUID chat_id);
}