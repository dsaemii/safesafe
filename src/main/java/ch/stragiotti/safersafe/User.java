package ch.stragiotti.safersafe;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "user_id")
    private int user_id;
    private String name;
    private String password;
    private String token;

    @OneToMany(mappedBy="user")
    private List<Password> passwords;

    public User(String name, String password, String token) {
        this.name = name;
        this.password = password;
        this.token = token;
    }


}
