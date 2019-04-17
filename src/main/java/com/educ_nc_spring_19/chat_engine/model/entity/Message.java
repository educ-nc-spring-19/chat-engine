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

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

    @Column(nullable = false, length = 2056)
    private MessageType type;

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

    public Message (String type, Chat chat, UUID ownerId, String text) {
        this.type = convertType(type);
        this.chat = chat;
        this.ownerId = ownerId;
        this.text = text;
        this.dateSending = OffsetDateTime.now();
    }

    private MessageType convertType (String type) {
        switch (type) {
            case "CHAT" :
                return MessageType.CHAT;
            case "JOIN" :
                return MessageType.JOIN;
            case "LEAVE" :
                return MessageType.LEAVE;
            default:
                return null;
        }
    }
}
