package com.example.demo.Service;


import com.example.demo.Repository.FarmerProfileRepository;
import com.example.demo.entity.FarmerProfile;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FarmerProfileService {

    private final FarmerProfileRepository farmerProfileRepository;

    public FarmerProfileService(FarmerProfileRepository farmerProfileRepository){
        this.farmerProfileRepository = farmerProfileRepository;
    }

    // Create or update farmer profile
    public FarmerProfile saveProfile(FarmerProfile profile) {

        Optional<FarmerProfile> existingProfile =
                farmerProfileRepository.findByUserId(profile.getUserId());

        if (existingProfile.isPresent()) {

            FarmerProfile oldProfile = existingProfile.get();

            oldProfile.setVillage(profile.getVillage());
            oldProfile.setTaluka(profile.getTaluka());
            oldProfile.setDistrict(profile.getDistrict());
            oldProfile.setState(profile.getState());
            oldProfile.getFarmSizeUnit();
            oldProfile.setFarmSizeUnit(profile.getFarmSizeUnit());
            oldProfile.setMilletCrops(profile.getMilletCrops());

            return farmerProfileRepository.save(oldProfile);
        }

        return farmerProfileRepository.save(profile);
    }

    // Get farmer profile by user ID
    public Optional<FarmerProfile> getProfileByUserId(String userId){
        return farmerProfileRepository.findByUserId(userId);
    }

}
