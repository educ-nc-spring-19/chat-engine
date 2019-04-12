package com.educ_nc_spring_19.chat_engine.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Data
@Table
public class Member {
    @Id
    @GeneratedValue
    @Column
    private UUID id;

    @Column(name = "User_Id", nullable = false)
    private UUID userId;

    @Basic(optional = false)
    @Column(name = "Date_Join", nullable = false, columnDefinition = "timestamp with time zone")
    private OffsetDateTime dateJoin;

    @Basic(optional = true)
    @Column(name = "Date_Left", nullable = true, columnDefinition = "timestamp with time zone")
    private OffsetDateTime dateLeft;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    public Member() {}

    public Member(UUID userId, Chat chat) {
        this.userId = userId;
        this.chat = chat;
        this.dateJoin = OffsetDateTime.now();
    }
}
