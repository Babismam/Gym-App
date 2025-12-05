package com.example.gymmanagement.services;

import com.example.gymmanagement.dto.MemberProgramDTO;
import com.example.gymmanagement.repositories.MemberProgramRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class MemberProgramService {

    @Inject
    private MemberProgramRepository memberProgramRepository;

    public List<MemberProgramDTO> getProgramsByMemberId(Long memberId) {
        return memberProgramRepository.findByMemberId(memberId)
                .stream()
                .map(MemberProgramDTO::new)
                .collect(Collectors.toList());
    }
}