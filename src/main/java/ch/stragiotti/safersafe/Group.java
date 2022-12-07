package ch.stragiotti.safersafe;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Group {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int group_id;
    private String name;
}
