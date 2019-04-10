package com.educ_nc_spring_19.chat_engine.model.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
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
    @Column(name = "Date_Join", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateJoin = new Date();

    @Basic(optional = false)
    @Column(name = "Date_Left")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateLeft;
}
