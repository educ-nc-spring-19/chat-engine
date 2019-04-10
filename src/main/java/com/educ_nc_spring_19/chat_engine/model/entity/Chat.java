package com.educ_nc_spring_19.chat_engine.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Entity
@Data
@Table(name = "Chat")
public class Chat {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Basic(optional = false)
    @Column(name = "Date_Creating", nullable = false, columnDefinition = "timestamp with time zone")
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime dateCreating = OffsetDateTime.now();

    @OneToMany
    private ArrayList<Member> members = new ArrayList<>();

    @OneToMany
    private ArrayList<Message> messages = new ArrayList<>();
}
