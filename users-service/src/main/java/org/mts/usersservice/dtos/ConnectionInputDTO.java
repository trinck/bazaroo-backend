package org.mts.usersservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionInputDTO {

    private Long id;
    private Date lastConnection;
    private String device;
    private String resolution;
    private String ip;
    private String os;
    private String navigator;
}
