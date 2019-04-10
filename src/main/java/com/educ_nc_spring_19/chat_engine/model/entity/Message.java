package com.educ_nc_spring_19.chat_engine.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "Message")
public class Message {
    @Id
    @GeneratedValue
    @Column(name = "Id")
    @org.hibernate.annotations.Type(type = "pg-uuid")
    private UUID id;

    @Column(name = "Text", nullable = false, length = 2056)
    private String text;

    @Column(name = "Owner_Id", nullable = false)
    @org.hibernate.annotations.Type(type = "pg-uuid")
    private UUID ownerId;

    @Basic(optional = false)
    @Column(name = "Date_Sending", nullable = false, columnDefinition = "timestamp with time zone")
    private OffsetDateTime dateSending;

    @Column(name = "Edited")
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
