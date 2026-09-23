package com.example.demo.Service;

import com.example.demo.Repository.BuyerProfileRepository;
import com.example.demo.entity.BuyerProfile;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BuyerProfileService {

    private final BuyerProfileRepository buyerProfileRepository;

    public BuyerProfileService(
            BuyerProfileRepository buyerProfileRepository) {

        this.buyerProfileRepository = buyerProfileRepository;
    }

    public BuyerProfile saveProfile(BuyerProfile profile) {

        Optional<BuyerProfile> existingProfile =
                buyerProfileRepository.findByUserId(profile.getUserId());

        if (existingProfile.isPresent()) {

            BuyerProfile oldProfile = existingProfile.get();

            oldProfile.setAddress(profile.getAddress());
            oldProfile.setCity(profile.getCity());
            oldProfile.setDistrict(profile.getDistrict());
            oldProfile.setState(profile.getState());
            oldProfile.setPinCode(profile.getPinCode());

            return buyerProfileRepository.save(oldProfile);
        }

        return buyerProfileRepository.save(profile);
    }

    public Optional<BuyerProfile> getProfileByUserId(String userId) {
        return buyerProfileRepository.findByUserId(userId);
    }
}