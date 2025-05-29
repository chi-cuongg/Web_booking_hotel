package org.codewithcuong.hamora.model;

import lombok.*;

import java.time.LocalDate;


@ToString
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private String id;
    private String fullname;
    private String email;
    private String password;
    private String phone;
    private String role;
    private boolean active;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
