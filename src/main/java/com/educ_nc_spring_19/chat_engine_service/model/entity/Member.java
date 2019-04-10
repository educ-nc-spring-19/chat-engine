package com.educ_nc_spring_19.chat_engine_service.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "Member")
public class Member {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Chat chat;

    @Column(name = "User_Id", nullable = false)
    private long userId;

    @Basic(optional = false)
    @Column(name = "Date_Join", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateJoin = new Date();

    @Basic(optional = false)
    @Column(name = "Date_Left")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateLeft;
}
