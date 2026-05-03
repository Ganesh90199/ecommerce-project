package com.ganesh.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ganesh.ecommerce.model.User;
import com.ganesh.ecommerce.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    // ✅ Register
    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        String msg = userService.register(user);
        return ResponseEntity.ok(Map.of("message", msg));
    }

    // ✅ Login
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        String result = userService.login(user);

        // ❌ Handle errors
        if (result.equals("User not found") || result.equals("Invalid password")) {
            return ResponseEntity.status(401).body(Map.of("error", result));
        }

        // ✅ Success → return token
        return ResponseEntity.ok(Map.of("token", result));
    }

    // 🔥 Admin creates admin (optional)
    @PostMapping("/admin/create-admin")
    public ResponseEntity<?> createAdmin(@RequestBody User user) {
        String msg = userService.createAdmin(user);
        return ResponseEntity.ok(Map.of("message", msg));
    }
}