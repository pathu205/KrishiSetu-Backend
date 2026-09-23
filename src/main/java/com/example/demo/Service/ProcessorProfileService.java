package com.example.demo.Service;

import com.example.demo.Repository.ProcessorProfileRepository;
import com.example.demo.entity.ProcessorProfile;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProcessorProfileService {

    private final ProcessorProfileRepository processorProfileRepository;

    public ProcessorProfileService(
            ProcessorProfileRepository processorProfileRepository) {

        this.processorProfileRepository = processorProfileRepository;
    }

    public ProcessorProfile saveProfile(ProcessorProfile profile) {

        Optional<ProcessorProfile> existingProfile =
                processorProfileRepository.findByUserId(profile.getUserId());

        if (existingProfile.isPresent()) {

            ProcessorProfile oldProfile = existingProfile.get();

            oldProfile.setBusinessName(profile.getBusinessName());
            oldProfile.setBusinessRegistrationNumber(profile.getBusinessRegistrationNumber());
            oldProfile.setBusinessType(profile.getBusinessType());
            oldProfile.setProcessingActivities(profile.getProcessingActivities());
            oldProfile.setAddress(profile.getAddress());
            oldProfile.setVillageOrCity(profile.getVillageOrCity());
            oldProfile.setDistrict(profile.getDistrict());
            oldProfile.setState(profile.getState());
            oldProfile.setGstNumber(profile.getGstNumber());

            return processorProfileRepository.save(oldProfile);
        }

        return processorProfileRepository.save(profile);
    }
    public Optional<ProcessorProfile> getProfileByUserId(String userId) {
        return processorProfileRepository.findByUserId(userId);
    }
}