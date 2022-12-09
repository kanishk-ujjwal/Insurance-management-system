package com.plum.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.plum.enums.ResponseStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddOrganisationsResponse implements Serializable {

    @JsonProperty("response_status")
    private ResponseStatusEnum status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("org_name")
    private String name;

    @JsonProperty("org_id")
    private Integer orgId;
}
