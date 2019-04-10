package com.educ_nc_spring_19.chat_engine.model.Entity;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "Member")
public class Member {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne
    private Chat chat;

    @Column(name = "User_Id", nullable = false)
    private UUID userId;

    @Basic(optional = false)
    @Column(name = "Date_Join", nullable = false, columnDefinition = "timestamp with time zone")
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime dateJoin = OffsetDateTime.now();

    @Basic(optional = false)
    @Column(name = "Date_Left", columnDefinition = "timestamp with time zone")
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime dateLeft;

    public Member(UUID userId, Chat chat) {
        this.userId = userId;
        this.chat = chat;
        this.dateJoin = OffsetDateTime.now();
    }
}
