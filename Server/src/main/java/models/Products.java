package models;

import java.io.Serializable;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Products implements Serializable {
    private int id;
    private int id_item;
    private int made_all_time;
    private int shipped_all_time;
    private int reserved;
    private int current_num;
    private String name;


}
