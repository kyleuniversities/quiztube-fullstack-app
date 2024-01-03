package com.ku.quizzical;

import java.time.Instant;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for Test related operations
 */
@CrossOrigin
@RestController
public final class TestController {

    @GetMapping("/test")
    public String test() {
        return "\"Time General >> " + Instant.now().toString() + "\"";
    }

    @GetMapping("/test/users-only")
    public String testUsersOnly() {
        return "\"Time Users Only >> " + Instant.now().toString() + "\"";
    }
}
