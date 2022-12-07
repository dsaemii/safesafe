package ch.stragiotti.safersafe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Password {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int password_id;
    private String url;
    private String username;
    private String password;
    private String note;

    @ManyToOne
    @JoinColumn(name="group_idfs", nullable = false)
    @JsonIgnore
    private Group group;

    @ManyToOne
    @JoinColumn(name="user_idfs", nullable = false)
    @JsonIgnore
    private User user;
}
