package com.example.demo.Controller;

import com.example.demo.Service.FarmerProfileService;
import com.example.demo.entity.FarmerProfile;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/farmer-profile")
public class FarmerProfileController {

    private final FarmerProfileService farmerProfileService;

    public FarmerProfileController(FarmerProfileService farmerProfileService){
        this.farmerProfileService = farmerProfileService;
    }

    // Create or update farmer profile
    @PostMapping
    public ResponseEntity<FarmerProfile> saveProfile(@Valid @RequestBody FarmerProfile profile, Authentication authentication){
        User user = (User) authentication.getPrincipal();

        // Only FARMER can create a farmer profile
        if(user.getRole() != Role.FARMER){
            return ResponseEntity.status(403).build();
        }

        // Profile belongs to logged-in farmer
        profile.setUserId(user.getId());

        FarmerProfile savedProfile = farmerProfileService.saveProfile(profile);

        return ResponseEntity.ok(savedProfile);
    }

    // Get logged-in farmer's profile
    @GetMapping
    public ResponseEntity<FarmerProfile> getProfile(Authentication authentication){
        User user = (User) authentication.getPrincipal();

        // Only FARMER can access this endpoint
        if(user.getRole() != Role.FARMER){
            return ResponseEntity.status(403).build();
        }

        return farmerProfileService
                .getProfileByUserId(user.getId())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



}
