package com.example.demo.Service;

import com.example.demo.Repository.SHGProfileRepository;
import com.example.demo.entity.SHGProfile;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SHGProfileService {

    private final SHGProfileRepository shgProfileRepository;

    public SHGProfileService(
            SHGProfileRepository shgProfileRepository) {

        this.shgProfileRepository = shgProfileRepository;
    }

    public SHGProfile saveProfile(SHGProfile profile) {

        Optional<SHGProfile> existingProfile =
                shgProfileRepository.findByUserId(profile.getUserId());

        if (existingProfile.isPresent()) {

            SHGProfile oldProfile = existingProfile.get();

            oldProfile.setShgName(profile.getShgName());
            oldProfile.setGroupRegistrationNumber(profile.getGroupRegistrationNumber());
            oldProfile.setVillage(profile.getVillage());
            oldProfile.setTaluka(profile.getTaluka());
            oldProfile.setDistrict(profile.getDistrict());
            oldProfile.setState(profile.getState());
            oldProfile.setNumberOfMembers(profile.getNumberOfMembers());
            oldProfile.setPrimaryActivity(profile.getPrimaryActivity());

            return shgProfileRepository.save(oldProfile);
        }

        return shgProfileRepository.save(profile);
    }

    public Optional<SHGProfile> getProfileByUserId(String userId) {
        return shgProfileRepository.findByUserId(userId);
    }
}