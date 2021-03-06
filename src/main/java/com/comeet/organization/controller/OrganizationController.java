package com.comeet.organization.controller;

import com.comeet.common.ApiResponse;
import com.comeet.organization.model.request.AddMemberRequestDto;
import com.comeet.organization.model.request.CreateOrganizationRequestDto;
import com.comeet.organization.model.response.OrganizationResponseDto;
import com.comeet.organization.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ApiResponse<OrganizationResponseDto> createOrganization(
        @RequestBody CreateOrganizationRequestDto createOrganizationRequestDto) {
        return ApiResponse.success(
            organizationService.createOrganization(createOrganizationRequestDto));
    }

    @GetMapping("/{id}")
    public ApiResponse<OrganizationResponseDto> getOrganization(
        @PathVariable Long id) {
        return ApiResponse.success(organizationService.getOrganization(id));
    }

    @PatchMapping("/{id}/in")
    public ApiResponse<OrganizationResponseDto> addMemberToOrganization(
        @PathVariable Long id,
        @RequestBody AddMemberRequestDto addMemberRequestDto) {
        return ApiResponse.success(
            organizationService.addMemberToOrganization(id, addMemberRequestDto));
    }

    @PatchMapping("/{id}/out")
    public ApiResponse<OrganizationResponseDto> removeMemberFromOrganization(
        @PathVariable Long id) {
        return ApiResponse.success(
            organizationService.removeMemberFromOrganization(id));
    }
}
