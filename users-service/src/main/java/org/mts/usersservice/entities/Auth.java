package org.mts.usersservice.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    protected String email;
    @Column(unique = true)
    protected String tel;
    protected Boolean active;
    private String password;
    protected Boolean verified;
    @OneToOne(mappedBy = "auth", cascade = CascadeType.ALL, orphanRemoval = true)
    private User user;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Role role;
    @OneToOne(mappedBy = "auth", cascade = CascadeType.PERSIST)
    private Connection connection;

}
