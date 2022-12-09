package com.plum.repo;

import com.plum.entity.OrganisationEnitity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganisationRepo extends JpaRepository<OrganisationEnitity, Long> {

    List<OrganisationEnitity> findAllByOrgNameAndPhnNumber(String orgName, String phnNo);

    List<OrganisationEnitity> findAllById(Integer orgId);
}
