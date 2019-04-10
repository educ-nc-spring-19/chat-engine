package com.educ_nc_spring_19.chat_engine.model.Entity;

import lombok.Data;

import javax.persistence.*;
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

    @OneToMany
    private ArrayList<Member> members = new ArrayList<>();

    @OneToMany
    private ArrayList<Message> messages = new ArrayList<>();
}
