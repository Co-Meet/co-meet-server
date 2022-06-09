package com.comeet.organization.service;

import com.comeet.config.security.SecurityUtil;
import com.comeet.member.entity.Member;
import com.comeet.member.exception.MemberNotFoundException;
import com.comeet.member.repository.MemberRepository;
import com.comeet.organization.entity.Organization;
import com.comeet.organization.exception.OrganizationNotFoundException;
import com.comeet.organization.model.request.AddMemberRequestDto;
import com.comeet.organization.model.request.CreateOrganizationRequestDto;
import com.comeet.organization.model.response.OrganizationResponseDto;
import com.comeet.organization.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public OrganizationResponseDto createOrganization(
        CreateOrganizationRequestDto createOrganizationRequestDto) {
        Member member = memberRepository.findById(SecurityUtil.resolveMemberId())
            .orElseThrow(MemberNotFoundException::new);
        Organization organization = Organization.of(createOrganizationRequestDto.getName());
        organization = organizationRepository.save(organization);
        member.addOrganization(organization);
        return new OrganizationResponseDto(organization.getId(), organization.getName(),
            organization.getMembers());
    }

    public OrganizationResponseDto getOrganization(Long id) {
        Organization organization = organizationRepository.findById(id)
            .orElseThrow(OrganizationNotFoundException::new);
        return new OrganizationResponseDto(organization.getId(), organization.getName(),
            organization.getMembers());
    }

    @Transactional
    public OrganizationResponseDto addMemberToOrganization(Long id,
        AddMemberRequestDto addMemberRequestDto) {
        Member member = memberRepository.findByNickname(addMemberRequestDto.getNickname())
            .orElseThrow(MemberNotFoundException::new);

        Organization organization = organizationRepository.findById(id)
            .orElseThrow(OrganizationNotFoundException::new);

        member.addOrganization(organization);
        return new OrganizationResponseDto(organization.getId(), organization.getName(),
            organization.getMembers());
    }

    @Transactional
    public OrganizationResponseDto removeMemberFromOrganization(Long id) {
        Member member = memberRepository.findById(SecurityUtil.resolveMemberId())
            .orElseThrow(MemberNotFoundException::new);

        Organization organization = organizationRepository.findById(id)
            .orElseThrow(OrganizationNotFoundException::new);

        member.removeOrganization(organization);
        return new OrganizationResponseDto(organization.getId(), organization.getName(),
            organization.getMembers());
    }
}
