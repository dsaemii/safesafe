package ch.stragiotti.safesave;

import ch.stragiotti.safesave.Model.Group;
import ch.stragiotti.safesave.Model.Password;
import ch.stragiotti.safesave.Model.User;
import ch.stragiotti.safesave.Repos.GroupRepository;
import ch.stragiotti.safesave.Repos.PasswordRepository;
import ch.stragiotti.safesave.Repos.UserRepository;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("https://localhost:3000")
public class SafeSafeRestController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordRepository passwordRepository;
    @Autowired
    private GroupRepository groupRepository;

    @PostMapping("signUp")
    public void signUp(@RequestBody String username, @RequestBody String password) {
        String token;
        String sha512 = Hashing.sha512()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
        System.out.println(sha512);

        // generate Token
        TokenAndCryptographicHandler tokenGenerator = new TokenAndCryptographicHandler();
        token = tokenGenerator.generateToken();
        User user = new User(0, username, sha512, token, null);

        userRepository.save(user);
    }

    @GetMapping("login")
    public String login(@RequestBody String username, @RequestBody String password) {
        String sha512 = Hashing.sha512()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
        System.out.println(sha512);

        String dbHash = userRepository.findByName(username).getPassword();
        if (dbHash.equals(password)) {
            return userRepository.findByName(username).getToken();
        } else {
            return "Wrong password!";
        }
    }

    @GetMapping("password")
    public List<Password> passwordList (@RequestHeader("Authorization") String token) {
        String authToken = token.substring("Bearer ".length()); //validate bearer token
        TokenAndCryptographicHandler cryptographicHandler = new TokenAndCryptographicHandler();
        // Process the request and return response
        Optional<User> user = userRepository.findByToken(authToken);
        if(user.isPresent()) {
            List<Password> passwords = passwordRepository.findByUser(user.get());
            for (Password pwd : passwords) {
                pwd.setPassword(cryptographicHandler.decrypt(pwd.getPassword()));
            }
            return passwords;
        } else {
            return new ArrayList<>();
        }
    }

    @PostMapping("password")
    public String createPassword(@RequestHeader("Authorization") String token, @RequestBody String username, @RequestBody String password, @RequestBody String url, @RequestBody String note, @RequestBody String group){
        String authToken = token.substring("Bearer ".length()); //validate bearer token
        TokenAndCryptographicHandler cryptographicHandler = new TokenAndCryptographicHandler();
        password = cryptographicHandler.encrypt(password);
        // Process the request and return response
        Optional<User> user = userRepository.findByToken(authToken);
        Optional<Group> pwdGroup = Optional.ofNullable(groupRepository.findByName(group));
        if(user.isPresent()) {
            Password pwd;
            if (pwdGroup.isPresent()){
                pwd = new Password(0, username, password, url, note, pwdGroup.get(), user.get());
            }else{
                pwd = new Password(0, username, password, url, note, null, user.get());
            }
            passwordRepository.save(pwd);
            return "Password Saved";
        } else {
            return "Access denied";
        }
    }

    @GetMapping("password/{id}")
    public Optional<Password> password (@PathVariable int id, @RequestHeader("Authorization") String token) {
        String authToken = token.substring("Bearer ".length()); //validate bearer token
        TokenAndCryptographicHandler cryptographicHandler = new TokenAndCryptographicHandler();
        // Process the request and return response
        Optional<User> user = userRepository.findByToken(authToken);
        if(user.isPresent()) {
            List<Password> passwords = passwordRepository.findByUser(user.get());
            Optional<Password> pwdFound = passwords.stream()
                    .filter(password -> id == password.getId())
                    .findAny();
            pwdFound.ifPresent(password -> password.setPassword(cryptographicHandler.decrypt(password.getPassword())));
            return pwdFound;
        } else {
            return null;
        }
    }

    @DeleteMapping("password/{id}")
    public String deletePassword (@PathVariable int id, @RequestHeader("Authorization") String token) {
        String authToken = token.substring("Bearer ".length()); //validate bearer token
        TokenAndCryptographicHandler cryptographicHandler = new TokenAndCryptographicHandler();
        // Process the request and return response
        Optional<User> user = userRepository.findByToken(authToken);
        if(user.isPresent()) {
            passwordRepository.deleteById(id);
            return "Password deleted";
        } else {
            return "Access denied";
        }
    }

    @PutMapping("password/{id}")
    public String updatePassword(@RequestHeader("Authorization") String token,@PathVariable int id, @RequestBody String username, @RequestBody String password, @RequestBody String url, @RequestBody String note, @RequestBody String group){
        String authToken = token.substring("Bearer ".length()); //validate bearer token
        TokenAndCryptographicHandler cryptographicHandler = new TokenAndCryptographicHandler();
        password = cryptographicHandler.encrypt(password);
        // Process the request and return response
        Optional<User> user = userRepository.findByToken(authToken);
        Optional<Group> pwdGroup = Optional.ofNullable(groupRepository.findByName(group));
        if(user.isPresent()) {
            Optional<Password> pwd = passwordRepository.findById(id);
            if (pwdGroup.isPresent()){
                pwd.get().setUsername(username);
                pwd.get().setPassword(password);
                pwd.get().setUrl(url);
                pwd.get().setNote(note);
                pwd.get().setGroupIdfs(pwdGroup.get());
                pwd.get().setUserIdfs(user.get());
            }else{
                pwd.get().setUsername(username);
                pwd.get().setPassword(password);
                pwd.get().setUrl(url);
                pwd.get().setNote(note);
                pwd.get().setGroupIdfs(null);
                pwd.get().setUserIdfs(user.get());
            }
            passwordRepository.save(pwd.get());
            return "Password Saved";
        } else {
            return "Access denied";
        }
    }

    @GetMapping("group")
    public List<Group> groupList (@RequestHeader("Authorization") String token) {
        String authToken = token.substring("Bearer ".length()); //validate bearer token
        // Process the request and return response
        Optional<User> user = userRepository.findByToken(authToken);
        if(user.isPresent()) {
            List<Group> passwords = groupRepository.findAll();
            return passwords;
        } else {
            return new ArrayList<>();
        }
    }

    @PostMapping("group")
    public String createGroup(@RequestHeader("Authorization") String token, @RequestBody String name){
        String authToken = token.substring("Bearer ".length()); //validate bearer token
        // Process the request and return response
        Optional<User> user = userRepository.findByToken(authToken);
        if(user.isPresent()) {
            groupRepository.save(new Group(0, name, null));
            return "Password Saved";
        } else {
            return "Access denied";
        }
    }

    @GetMapping("group/{id}")
    public Optional<Group> getGroup(@PathVariable int id, @RequestHeader("Authorization") String token) {
        String authToken = token.substring("Bearer ".length()); //validate bearer token
        // Process the request and return response
        Optional<User> user = userRepository.findByToken(authToken);
        if(user.isPresent()) {
            Optional<Group> group = groupRepository.findById(id);
            return group;
        } else {
            return null;
        }
    }

    @DeleteMapping("group/{id}")
    public String deleteGroup(@PathVariable int id, @RequestHeader("Authorization") String token) {
        String authToken = token.substring("Bearer ".length()); //validate bearer token
        TokenAndCryptographicHandler cryptographicHandler = new TokenAndCryptographicHandler();
        // Process the request and return response
        Optional<User> user = userRepository.findByToken(authToken);
        if(user.isPresent()) {
            passwordRepository.deleteById(id);
            return "Password deleted";
        } else {
            return "Access denied";
        }
    }

    @PutMapping("group/{id}")
    public String updateGroup(@RequestHeader("Authorization") String token, @PathVariable int id, @RequestBody String name){
        String authToken = token.substring("Bearer ".length()); //validate bearer token
        TokenAndCryptographicHandler cryptographicHandler = new TokenAndCryptographicHandler();

        // Process the request and return response
        Optional<User> user = userRepository.findByToken(authToken);
        if(user.isPresent()) {
            Optional<Group> group = groupRepository.findById(id);
            group.get().setName(name);
            groupRepository.save(group.get());
            return "Password Saved";
        } else {
            return "Access denied";
        }
    }
}
