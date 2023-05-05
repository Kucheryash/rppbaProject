package models;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Bills implements Serializable {
    private int id;
    private int id_order;
    private int id_client;
    private double cost;
    private String state;
    private String payment_meth;
}
