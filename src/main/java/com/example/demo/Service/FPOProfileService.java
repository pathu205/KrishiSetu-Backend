package com.example.demo.Service;

import com.example.demo.Repository.FPOProfileRepository;
import com.example.demo.entity.FPOProfile;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FPOProfileService {

    private final FPOProfileRepository fpoProfileRepository;

    public FPOProfileService(
            FPOProfileRepository fpoProfileRepository) {

        this.fpoProfileRepository = fpoProfileRepository;
    }

    public FPOProfile saveProfile(FPOProfile profile) {

        Optional<FPOProfile> existingProfile =
                fpoProfileRepository.findByUserId(profile.getUserId());

        if (existingProfile.isPresent()) {

            FPOProfile oldProfile = existingProfile.get();

            oldProfile.setFpoName(profile.getFpoName());
            oldProfile.setRegistrationNumber(profile.getRegistrationNumber());
            oldProfile.setVillage(profile.getVillage());
            oldProfile.setTaluka(profile.getTaluka());
            oldProfile.setDistrict(profile.getDistrict());
            oldProfile.setState(profile.getState());
            oldProfile.setNumberOfFarmers(profile.getNumberOfFarmers());
            return fpoProfileRepository.save(oldProfile);
        }

        return fpoProfileRepository.save(profile);
    }

    public Optional<FPOProfile> getProfileByUserId(String userId) {
        return fpoProfileRepository.findByUserId(userId);
    }
}