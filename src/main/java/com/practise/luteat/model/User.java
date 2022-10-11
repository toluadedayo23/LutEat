package com.practise.luteat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @NotEmpty
    @Size(min = 2, max = 10)
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty
    @Size(min = 2, max = 10)
    private String lastName;

    @NotEmpty
    @Size(min = 2, max = 10)
    private String username;

    private String password;

    @Email
    private String email;

    @NotEmpty
    @Size(min = 11, max = 13)
    private String phoneNumber;

    @Column(name = "created_date")
    private Instant createdDate;

    private Boolean enabled;
}
