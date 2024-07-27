package com.licenta.demo.service;

import com.licenta.demo.entity.Clinic;
import com.licenta.demo.repository.ClinicRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ClinicService {
    private final ClinicRepository clinicRepository;

    public List<Clinic> findAll() {
        return clinicRepository.findAll();
    }

    public void saveClinic(Clinic clinic) {
        clinicRepository.save(clinic);
    }

    public void deleteClinicById(Long id)
            throws EntityNotFoundException {

        Optional<Clinic> clinic = clinicRepository.findById(id);
        if (clinic.isPresent()) {
            clinicRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException
                    ("No clinic record exists for this ID.");
        }
    }
}
