package com.example.demo.Controller;

import com.example.demo.Service.ProcessorProfileService;
import com.example.demo.entity.ProcessorProfile;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/processor-profile")
public class ProcessorProfileController {

    private final ProcessorProfileService processorProfileService;

    public ProcessorProfileController(
            ProcessorProfileService processorProfileService) {

        this.processorProfileService = processorProfileService;
    }

    @PostMapping
    public ResponseEntity<ProcessorProfile> saveProfile(
            @Valid
            @RequestBody ProcessorProfile profile,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        System.out.println(
                "Processor POST user = "
                        + user.getEmail()
                        + " | role = "
                        + user.getRole()
        );

        if (user.getRole() != Role.PROCESSOR) {
            return ResponseEntity.status(403).build();
        }

        profile.setUserId(user.getId());

        ProcessorProfile savedProfile =
                processorProfileService.saveProfile(profile);

        return ResponseEntity.ok(savedProfile);
    }

    @GetMapping
    public ResponseEntity<ProcessorProfile> getProfile(
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        if (user.getRole() != Role.PROCESSOR) {
            return ResponseEntity.status(403).build();
        }

        return processorProfileService
                .getProfileByUserId(user.getId())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}