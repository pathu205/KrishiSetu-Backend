package com.example.demo.Controller;

import com.example.demo.Service.BuyerProfileService;
import com.example.demo.entity.BuyerProfile;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/buyer-profile")
public class BuyerProfileController {

    private final BuyerProfileService buyerProfileService;

    public BuyerProfileController(
            BuyerProfileService buyerProfileService) {

        this.buyerProfileService = buyerProfileService;
    }

    @PostMapping
    public ResponseEntity<BuyerProfile> saveProfile(
            @Valid
            @RequestBody BuyerProfile profile,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        if (user.getRole() != Role.BUYER) {
            return ResponseEntity.status(403).build();
        }

        profile.setUserId(user.getId());

        BuyerProfile savedProfile =
                buyerProfileService.saveProfile(profile);

        return ResponseEntity.ok(savedProfile);
    }

    @GetMapping
    public ResponseEntity<BuyerProfile> getProfile(
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        if (user.getRole() != Role.BUYER) {
            return ResponseEntity.status(403).build();
        }

        return buyerProfileService
                .getProfileByUserId(user.getId())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}