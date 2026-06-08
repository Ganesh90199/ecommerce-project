package com.ganesh.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ganesh.ecommerce.model.User;
import com.ganesh.ecommerce.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    // ✅ REGISTER
    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        String msg = userService.register(user);
        return ResponseEntity.ok(Map.of("message", msg));
    }

    // ✅ LOGIN (FIXED)
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        User loggedInUser = userService.loginUser(user);

        if (loggedInUser == null) {
            return ResponseEntity.status(401)
                    .body(Map.of("error", "Invalid credentials"));
        }

        String token = userService.generateToken(
                loggedInUser.getEmail(),
                loggedInUser.getRole()
        );

        return ResponseEntity.ok(Map.of(
                "token", token,
                "role", loggedInUser.getRole()   // 🔥 VERY IMPORTANT
        ));
    }

    // ✅ ADMIN CREATE
    @PostMapping("/admin/create-admin")
    public ResponseEntity<?> createAdmin(@RequestBody User user) {
        String msg = userService.createAdmin(user);
        return ResponseEntity.ok(Map.of("message", msg));
    }
}