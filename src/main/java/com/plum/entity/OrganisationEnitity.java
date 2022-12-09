package com.plum.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "organisation_details")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrganisationEnitity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "org_name")
    private String orgName;

    @Column(name = "address")
    private String addess;

    @Column(name = "phn_number")
    private String phnNumber;

    @Column(name = "email_id")
    private String email_id;
}
