package com.plum.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "employee")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeEntity implements Serializable {

    @Id
    private Integer id;

    @NotNull
    @JsonProperty("first_name")
    @Column(name = "first_name")
    private String firstName;

    @JsonProperty("middle_name")
    @Column(name = "middle_name")
    private String middleName;

    @JsonProperty("last_name")
    @Column(name = "last_name")
    @NotNull
    private String lastName;

    @JsonProperty("date_of_birth")
    @Column(name = "date_of_birth")
    @NotNull
    private String dateOfBirth;

    @NotNull
    @JsonProperty("gender")
    @Column(name = "gender")
    private String gender;

    @JsonProperty("email_id")
    @Column(name = "email_id")
    private String emailID;

    @JsonProperty("org_id")
    @Column(name = "org_id")
    private Integer orgId;
}
