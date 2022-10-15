package com.practise.luteat.model;

import com.practise.luteat.customValidator.Password;
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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "first_name")
    @NotEmpty
    @Size(min = 2, max = 10)
    private String firstname;

    @Column(name = "last_name")
    @NotEmpty
    @Size(min = 2, max = 10)
    private String lastname;

    @NotEmpty
    @Size(min = 2, max = 10)
    private String username;

    @NotEmpty(message = "password cannot be empty")
    @Password
    private String password;

    @Email
    private String email;

    @NotEmpty
    @Size(min = 11, max = 13)
    @Column(name = "phonenumber")
    private String phonenumber;

    @Column(name = "created_date")
    private Instant createdDate;

    private Boolean enabled;
}
