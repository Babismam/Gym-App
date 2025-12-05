package com.example.gymmanagement.services;

import com.example.gymmanagement.entities.OpeningHours;
import com.example.gymmanagement.repositories.OpeningHoursRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class OpeningHoursService {
    @Inject
    private OpeningHoursRepository repository;

    public List<OpeningHours> getOpeningHours() {
        return repository.findAllOrdered();
    }
}