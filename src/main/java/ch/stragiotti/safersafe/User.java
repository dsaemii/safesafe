package ch.stragiotti.safersafe;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int user_id;
    private String name;
    private String password;
    private String token;

    @OneToMany(mappedBy="user")
    private List<Password> passwords;
}
