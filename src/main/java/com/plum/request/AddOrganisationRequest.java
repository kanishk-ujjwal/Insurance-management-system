package com.plum.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddOrganisationRequest implements Serializable {

    @JsonProperty("org_name")
    private String orgName;

    @JsonProperty("address")
    private String address;

    @JsonProperty("contact_number")
    private String contactNumber;

    @JsonProperty("email")
    private String email_id;
}
