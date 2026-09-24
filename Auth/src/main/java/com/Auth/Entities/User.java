package com.Auth.Entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.Auth.Enums.Role;


@Entity
@Setter
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role=Role.Customer;

    private boolean active = true;
}