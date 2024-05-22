package org.mts.usersservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String username;
    private String password;
    @OneToOne(mappedBy = "auth", cascade = CascadeType.ALL, orphanRemoval = true)
    private User user;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Role role;
    @OneToOne(mappedBy ="auth" , cascade = CascadeType.PERSIST)
    private Connection connection;

}
