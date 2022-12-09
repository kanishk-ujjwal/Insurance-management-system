package com.plum.service;

import com.plum.constants.HelperConstants;
import com.plum.entity.OrganisationEnitity;
import com.plum.enums.ResponseStatusEnum;
import com.plum.repo.OrganisationRepo;
import com.plum.request.AddOrganisationRequest;
import com.plum.response.AddOrganisationsResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class OrganisationsService {

    @Autowired
    private OrganisationRepo organisationRepo;

    /**
     * This method performs the required checks on request body data and stores it in the db
     * @param addOrganisationRequest
     * @return the response if the organisation is added successfully or not
     */
    public AddOrganisationsResponse createOrganisationEntry(AddOrganisationRequest addOrganisationRequest) {
        if (!preCheck(addOrganisationRequest)) {
            return AddOrganisationsResponse.builder()
                    .status(ResponseStatusEnum.UNPROCESSED)
                    .message(HelperConstants.UNPROCESSED_MSG)
                    .name("invalid")
                    .orgId(-1)
                    .build();
        }
        OrganisationEnitity organisationEnitity = getOrganisation(addOrganisationRequest);
        organisationEnitity = organisationRepo.save(organisationEnitity);
        return AddOrganisationsResponse.builder()
                .status(ResponseStatusEnum.SUCCESS)
                .message(HelperConstants.SUCCESS_MSG)
                .name(organisationEnitity.getOrgName())
                .orgId(organisationEnitity.getId())
                .build();
    }

    public OrganisationEnitity getOrganisation(AddOrganisationRequest addOrganisationRequest) {
        OrganisationEnitity organisationEnitity = new OrganisationEnitity();
        organisationEnitity.setOrgName(addOrganisationRequest.getOrgName());
        organisationEnitity.setAddess(addOrganisationRequest.getAddress());
        organisationEnitity.setEmail_id(addOrganisationRequest.getEmail_id());
        organisationEnitity.setPhnNumber(addOrganisationRequest.getContactNumber());
        return organisationEnitity;
    }

    private Boolean preCheck(AddOrganisationRequest addOrganisationRequest) {
        if (addOrganisationRequest == null || ObjectUtils.isEmpty(addOrganisationRequest))
            return false;
        return !StringUtils.isBlank(addOrganisationRequest.getOrgName()) || !isAlreadyPresent(
                addOrganisationRequest.getOrgName(), addOrganisationRequest.getContactNumber());
    }

    /**
     * This method checks if the org already exists in the db
     * @param orgName
     * @param phnNumber
     * @return true if org already exists
     */
    private Boolean isAlreadyPresent(String orgName, String phnNumber) {
        List<OrganisationEnitity> organisationEnitityList =
                organisationRepo.findAllByOrgNameAndPhnNumber(orgName, phnNumber);
        return !CollectionUtils.isEmpty(organisationEnitityList);
    }

    public Boolean isOrgPresent(Integer orgId) {
        List<OrganisationEnitity> organisationEnitityList =
                organisationRepo.findAllById(orgId);
        return !CollectionUtils.isEmpty(organisationEnitityList);
    }

}
