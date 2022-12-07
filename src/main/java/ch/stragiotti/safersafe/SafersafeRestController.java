package ch.stragiotti.safersafe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("https://localhost:3000")
public class SafersafeRestController {

    @Autowired
    private SafersafeRepository safersafeRepository;

    //get... token

}
