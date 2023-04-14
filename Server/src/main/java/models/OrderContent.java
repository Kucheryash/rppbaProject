package models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderContent {
    private int id;
    private int id_order;
    private int id_item;
    private int number;
}
