package models;

import lombok.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Users implements Serializable {
    private int id;
    private String login;
    private String password;
    private int role;

    public Users(String login, String password, int role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
}
