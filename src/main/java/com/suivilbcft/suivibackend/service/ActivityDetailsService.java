package com.suivilbcft.suivibackend.service;

import com.suivilbcft.suivibackend.model.Institution;
import com.suivilbcft.suivibackend.repository.InstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ActivityDetailsService {

    @Autowired
    private InstitutionRepository institutionRepository;

    /**
     * Validate that required basic information is completed before allowing activity details
     */
    public boolean isBasicInfoCompleted(String institutionId) {
        Optional<Institution> institutionOpt = institutionRepository.findById(institutionId);
        if (institutionOpt.isPresent()) {
            return institutionOpt.get().getCompletedInfoAt() != null;
        }
        return false;
    }
}