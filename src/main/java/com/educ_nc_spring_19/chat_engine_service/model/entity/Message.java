package com.educ_nc_spring_19.chat_engine_service.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "Message")
public class Message {
    @Setter
    @Getter
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Chat chat;

    @Column(name = "Text", nullable = false, length = 2056)
    private String text;

    @Column(name = "Owner_Id", nullable = false)
    private long ownerId;

    @Basic(optional = false)
    @Column(name = "Date_Sending", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateSending = new Date();

    @Column(name = "Edited")
    private boolean edited;

    public Message (Chat chat, long ownerId, String text) {
        this.chat = chat;
        this.ownerId = ownerId;
        this.text = text;
        this.dateSending = new Date();
    }
}
