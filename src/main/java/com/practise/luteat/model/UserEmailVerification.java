package com.practise.luteat.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@Entity
@Table(name = "email verification")
public class UserEmailVerification {

    @Id
    @GeneratedValue(generator = "UUID_GENERATOR")
    @GenericGenerator(name = "UUID_GENERATOR", strategy = "org.hibernate.id.UUIDGenerator")
    private String token;

    private String username;

    @Column(name = "expiry_date")
    private Instant expiryDate;

    public UserEmailVerification(String username){
        this.username = username;
    }
}
