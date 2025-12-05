package com.example.gymmanagement.services;

import com.example.gymmanagement.entities.Program;
import com.example.gymmanagement.repositories.ProgramRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ProgramService {

    @Inject
    private ProgramRepository programRepository;

    public List<Program> getAllPrograms() {
        return programRepository.findAll();
    }

    public Program getProgramById(Long id) {
        return programRepository.findById(id);
    }

    @Transactional
    public Program createProgram(Program program) {
        programRepository.save(program);
        return program;
    }

    @Transactional
    public Program updateProgram(Program program) {
        programRepository.save(program);
        return program;
    }

    @Transactional
    public void deleteProgram(Long id) {
        Program program = programRepository.findById(id);
        if (program != null) {
            programRepository.delete(program);
        }
    }
    public long countPrograms() {
        return programRepository.countAll();
    }
}