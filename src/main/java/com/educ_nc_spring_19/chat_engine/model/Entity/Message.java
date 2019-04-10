package com.educ_nc_spring_19.chat_engine.model.Entity;

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
    @Setter
    @Getter
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne
    private Chat chat;

    @Column(name = "Text", nullable = false, length = 2056)
    private String text;

    @Column(name = "Owner_Id", nullable = false)
    private UUID ownerId;

    @Basic(optional = false)
    @Column(name = "Date_Sending", nullable = false, columnDefinition = "timestamp with time zone")
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime dateSending = OffsetDateTime.now();

    @Column(name = "Edited")
    private boolean edited;

    public Message (Chat chat, UUID ownerId, String text) {
        this.chat = chat;
        this.ownerId = ownerId;
        this.text = text;
        this.dateSending = OffsetDateTime.now();
    }
}
