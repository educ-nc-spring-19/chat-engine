package com.educ_nc_spring_19.chat_engine.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Data
@Table
public class Message {
    @Id
    @GeneratedValue
    @Column
    private UUID id;

    @Column(nullable = false, length = 2056)
    private String text;

    @Column(name = "Owner_Id", nullable = false)
    private UUID ownerId;

    @Basic(optional = false)
    @Column(name = "Date_Sending", nullable = false, columnDefinition = "timestamp with time zone")
    private OffsetDateTime dateSending;

    @Column
    private boolean edited;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    public Message() {}

    public Message (Chat chat, UUID ownerId, String text) {
        this.chat = chat;
        this.ownerId = ownerId;
        this.text = text;
        this.dateSending = OffsetDateTime.now();
    }
}
