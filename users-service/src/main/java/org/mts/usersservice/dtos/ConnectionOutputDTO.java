package org.mts.usersservice.dtos;

import jakarta.annotation.Nonnull;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mts.usersservice.entities.User;

import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionOutputDTO {

    private Long id;
    private Date lastConnection;
    private String device;
    private String resolution;
    private String ip;
    private String os;
    private String navigator;

}
