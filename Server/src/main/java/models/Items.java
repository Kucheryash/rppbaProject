package models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Items {
    private int id;
    private String name;
    private String type;
    private double cost;
    private String check;
    private String items_group;
    private String measure;
    private String bar_code;
    private int id_prod;
    private int id_search;
}
