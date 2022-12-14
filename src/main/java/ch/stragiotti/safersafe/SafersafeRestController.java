package ch.stragiotti.safersafe;

import com.google.common.hash.Hashing;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@CrossOrigin("https://localhost:3000")
public class SafersafeRestController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordRepository passwordRepository;

    @GetMapping("login")
    public String login(@RequestBody String username, @RequestBody String password) {
        String token;
        String sha512 = Hashing.sha512()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
        System.out.println(sha512);

        String dbHash = userRepository.findByName(username).getPassword();
        if (dbHash.equals(password)) {
            return userRepository.findByName(username).getToken();
        } else {
            return "nope";
        }

    }

    @RequestMapping(value="/passwords", method=RequestMethod.GET)
    public List<Password> passwordList (@RequestHeader("Authorization") String token) {
        String authToken = token.substring("Bearer ".length()); //validate bearer token
        // Process the request and return response
        Optional<User> user = userRepository.findByToken(authToken);
        if(user.isPresent()) {
            List<Password> passwords = passwordRepository.findByUser(user.get());
            return passwords;
        } else {
            return new ArrayList<>();
        }
    }

    @GetMapping("passwords/{id}")
    public Optional<Password> password (@PathVariable int id, @RequestHeader("Authorization") String token) {
        String authToken = token.substring("Bearer ".length()); //validate bearer token
        // Process the request and return response
        Optional<User> user = userRepository.findByToken(authToken);
        if(user.isPresent()) {
            List<Password> passwords = passwordRepository.findByUser(user.get());
            return passwords.stream()
                    .filter(password -> id == password.getPassword_id())
                    .findAny();
        } else {
            return null;
        }
    }

    @PostMapping("password")
    public void createPassword (@RequestHeader("Authorization") String token, @RequestBody Password password) {
        String authToken = token.substring("Bearer ".length()); //validate bearer token
        // Process the request and return response
        Optional<User> user = userRepository.findByToken(authToken);
        if(user.isPresent()) {
            passwordRepository.save(password);
        }
    }

    @DeleteMapping("delete")
    public void deletePassword (@RequestHeader("Authorization") String token, @RequestBody Password password) {
        String authToken = token.substring("Bearer ".length()); //validate bearer token
        // Process the request and return response
        Optional<User> user = userRepository.findByToken(authToken);
        if(user.isPresent()) {
            passwordRepository.delete(password);
        }
    }

    @PostUpdate("update")
    public void updatePassword (@RequestHeader("Authorization") String token, @RequestBody Password password) {
        String authToken = token.substring("Bearer ".length()); //validate bearer token
        // Process the request and return response
        Optional<User> user = userRepository.findByToken(authToken);
        if(user.isPresent()) {
            passwordRepository.;
        }
    }

}
