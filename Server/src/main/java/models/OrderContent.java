package models;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderContent implements Serializable {
    private int id;
    private int id_order;
    private int id_item;
    private int number;
    private String item_name;
}
