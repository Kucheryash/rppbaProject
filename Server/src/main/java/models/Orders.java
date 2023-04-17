package models;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Orders implements Serializable {
    private int id;
    private int id_client;
    private String state;
    private int manager;
    private String shipped_date;
    private String delivery_date;
    private int transp_time;
}
