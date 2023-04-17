package models;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Clients implements Serializable {
    private int id;
    private String name;
    private String email;
    private String phone;

}
