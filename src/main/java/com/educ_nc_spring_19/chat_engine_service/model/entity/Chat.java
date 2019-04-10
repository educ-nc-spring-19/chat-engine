package com.educ_nc_spring_19.chat_engine_service.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Data
@Table(name = "Chat")
public class Chat {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany
    private ArrayList<Member> members = new ArrayList<>();

    @OneToMany
    private ArrayList<Message> messages = new ArrayList<>();
}
