package ch.stragiotti.safersafe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@CrossOrigin("https://localhost:3000")
public class SafersafeRestController {

    @Autowired
    private PasswordRepository safersafeRepository;

    @GetMapping("login")
    public String login(@RequestBody String username, @RequestBody String password) {
        String token;
        token = StreamSupport.stream(safersafeRepository
                .findAll()
        )
        PasswordRepository.stream
    }

}
