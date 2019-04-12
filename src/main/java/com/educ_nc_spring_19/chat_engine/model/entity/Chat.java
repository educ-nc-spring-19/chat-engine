package com.educ_nc_spring_19.chat_engine.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table
public class Chat {
    @Id
    @GeneratedValue
    @Column
    private UUID id;

    @Column
    private UUID hostId;

    @Basic(optional = false)
    @Column(name = "Date_Creating", nullable = false, columnDefinition = "timestamp with time zone")
    private OffsetDateTime dateCreating = OffsetDateTime.now();

    @JsonIgnore
    @OneToMany(mappedBy = "chat")
    private List<Member> members = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "chat")
    private List<Message> messages= new ArrayList<>();

    public Chat() {}
}
