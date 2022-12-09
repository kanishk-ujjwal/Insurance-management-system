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
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddEmployeeResponse implements Serializable {

    @JsonProperty("response_status")
    private ResponseStatusEnum status;

    @JsonProperty("success_count")
    private Integer sucessCount;

    @JsonProperty("failure_count")
    private Integer failureCount;

    @JsonProperty("error_index")
    private List<Integer> errorIndex;
}
