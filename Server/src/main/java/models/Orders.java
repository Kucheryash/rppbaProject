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
    private String client_email;
    private String manager_login;

    public Orders(int id_client, String state, int manager, String delivery_date, int transp_time) {
        this.id_client = id_client;
        this.state = state;
        this.manager = manager;
        this.delivery_date = delivery_date;
        this.transp_time = transp_time;
    }

    public Orders(int id, String delivery_date, int transp_time) {
        this.id = id;
        this.delivery_date = delivery_date;
        this.transp_time = transp_time;
    }
}
