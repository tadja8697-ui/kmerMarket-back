package com.kmermarket.kmerMarket.Controllers;

import com.kmermarket.kmerMarket.Entities.Users;
import com.kmermarket.kmerMarket.Repositories.UsersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {

    private final UsersRepo usersRepo;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Users user) {
        if (usersRepo.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Un compte existe déjà avec cet email");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepo.save(user);

        return ResponseEntity.ok("Inscription réussie");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users loginRequest) {
        return usersRepo.findByEmail(loginRequest.getEmail())
                .filter(user -> passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
                .map(user -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("id", user.getId());
                    response.put("name", user.getName());
                    response.put("email", user.getEmail());
                    response.put("phone", user.getPhone());
                    response.put("message", "Connexion réussie, bienvenue " + user.getName());
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Email ou mot de passe incorrect")));
    }
}
