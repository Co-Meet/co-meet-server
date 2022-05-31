package com.comeet.organization.controller;

import com.comeet.common.ApiResponse;
import com.comeet.organization.model.request.AddMemberRequestDto;
import com.comeet.organization.model.request.CreateOrganizationRequestDto;
import com.comeet.organization.model.response.OrganizationInfoResponseDto;
import com.comeet.organization.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @PostMapping()
    public ApiResponse<OrganizationInfoResponseDto> createOrganization(
        @RequestBody CreateOrganizationRequestDto createOrganizationRequestDto) {
        return ApiResponse.success(
            organizationService.createOrganization(createOrganizationRequestDto));
    }

    @GetMapping("/{id}")
    public ApiResponse<OrganizationInfoResponseDto> getOrganizationInfo(
        @PathVariable Long id) {
        return ApiResponse.success(organizationService.getOrganizationInfo(id));
    }

    @PatchMapping("/{id}")
    public ApiResponse<OrganizationInfoResponseDto> addMember(
        @PathVariable Long id,
        @RequestBody AddMemberRequestDto addMemberRequestDto) {
        return ApiResponse.success(
            organizationService.addMember(id, addMemberRequestDto));
    }
}
