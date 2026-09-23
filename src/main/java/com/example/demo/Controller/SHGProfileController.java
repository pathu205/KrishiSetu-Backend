package com.example.demo.Controller;

import com.example.demo.Service.SHGProfileService;
import com.example.demo.entity.Role;
import com.example.demo.entity.SHGProfile;
import com.example.demo.entity.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shg-profile")
public class SHGProfileController {

    private final SHGProfileService shgProfileService;

    public SHGProfileController(
            SHGProfileService shgProfileService) {

        this.shgProfileService = shgProfileService;
    }

    @PostMapping
    public ResponseEntity<SHGProfile> saveProfile(
            @Valid
            @RequestBody SHGProfile profile,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        if (user.getRole() != Role.SHG) {
            return ResponseEntity.status(403).build();
        }

        profile.setUserId(user.getId());

        SHGProfile savedProfile =
                shgProfileService.saveProfile(profile);

        return ResponseEntity.ok(savedProfile);
    }

    @GetMapping
    public ResponseEntity<SHGProfile> getProfile(
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        if (user.getRole() != Role.SHG) {
            return ResponseEntity.status(403).build();
        }

        return shgProfileService
                .getProfileByUserId(user.getId())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}