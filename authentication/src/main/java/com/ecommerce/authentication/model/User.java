package com.ecommerce.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank(message = "Username must not be blank.")
    @Size(min = 3, max = 64, message = "Username length should be between 3 and 64.")
    @Column(length = 64)
    private String username;

    @NotBlank(message = "Password must not be blank.")
    @Size(min = 3, max = 64, message = "Password length should be between 3 and 64.")
    @Column(length = 64)
    private String password;

    @NotBlank(message = "Name must not be blank.")
    @Size(min = 3, max = 64, message = "Name length should be between 3 and 64.")
    @Column(length = 64)
    private String name;

    @NotBlank(message = "Address must not be blank.")
    @Size(min = 3, max = 64, message = "Address length should be between 3 and 64.")
    @Column(length = 64)
    private String address;

}
