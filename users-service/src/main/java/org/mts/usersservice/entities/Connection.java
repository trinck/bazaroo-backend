package org.mts.usersservice.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Connection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date lastConnection;
    private String device;
    private String resolution;
    private String ip;
    private String os;
    private String navigator;
    @OneToOne
    private Auth auth;
}
