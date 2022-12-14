package ch.stragiotti.safersafe;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Group {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int group_id;
    private String name;

    @OneToMany(mappedBy="group")
    private List<Password> passwords;
}
