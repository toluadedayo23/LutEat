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
import java.util.HashSet;
import java.util.Set;

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
    private String password;

    @Email(message = "please provide a valid email")
    private String email;

    @NotEmpty
    @Size(min = 11, max = 13)
    @Column(name = "phonenumber")
    private String phonenumber;

    @Column(name = "created_date")
    private Instant createdDate;

    @Size(min = 1, message = "User must have at least one role")
    @JoinTable(name = "user_roles",
               joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"),
               inverseJoinColumns = @JoinColumn(name ="role_id", referencedColumnName = "roleId")
    )
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();

    private Boolean enabled;
}
