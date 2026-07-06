package com.htv.user.domain;

import com.htv.security.HtvSecurityProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String passwordHash;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> permissions = new HashSet<>();

    private boolean mfaEnabled;

    @Enumerated(EnumType.STRING)
    public HtvSecurityProperties.MfaMethod preferredMfaEnabled;
}
