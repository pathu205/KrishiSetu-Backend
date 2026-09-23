package com.example.demo.Controller;

import com.example.demo.Service.FPOProfileService;
import com.example.demo.entity.FPOProfile;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fpo-profile")
public class FPOProfileController {

    private final FPOProfileService fpoProfileService;

    public FPOProfileController(
            FPOProfileService fpoProfileService) {

        this.fpoProfileService = fpoProfileService;
    }

    @PostMapping
    public ResponseEntity<FPOProfile> saveProfile(
            @Valid
            @RequestBody FPOProfile profile,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        if (user.getRole() != Role.FPO) {
            return ResponseEntity.status(403).build();
        }

        profile.setUserId(user.getId());

        FPOProfile savedProfile =
                fpoProfileService.saveProfile(profile);

        return ResponseEntity.ok(savedProfile);
    }

    @GetMapping
    public ResponseEntity<FPOProfile> getProfile(
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        if (user.getRole() != Role.FPO) {
            return ResponseEntity.status(403).build();
        }

        return fpoProfileService
                .getProfileByUserId(user.getId())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}