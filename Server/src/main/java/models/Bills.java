package models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Bills {
    private int id;
    private int id_order;
    private int id_client;
    private double cost;
    private String state;
    private String payment_meth;
}
