package blank.blank.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import blank.blank.models.UserModel;
import blank.blank.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody UserModel user) {
        Map<String, String> response = new HashMap<>();
        try {
            userService.registerUser(user);
            response.put("message", "User registered successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserModel user) {
        Map<String, String> response = new HashMap<>();
        
        // Validate input
        if (user.getUsername() == null || user.getPassword() == null) {
            response.put("message", "Username and password cannot be null");
            return ResponseEntity.badRequest().body(response);
        }

        // Attempt login
        String token = userService.login(user.getUsername(), user.getPassword());
        
        if (token != null) {
            response.put("token", token);  // Return JWT token if login is successful
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Invalid username or password");
            return ResponseEntity.status(401).body(response);  // Unauthorized if login fails
        }
    }


    

}
