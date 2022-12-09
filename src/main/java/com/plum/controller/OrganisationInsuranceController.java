package com.plum.controller;

import com.plum.enums.ResponseStatusEnum;
import com.plum.request.AddEmployeeRequest;
import com.plum.request.AddOrganisationRequest;
import com.plum.response.AddEmployeeResponse;
import com.plum.response.AddOrganisationsResponse;
import com.plum.response.PaginatedResponse;
import com.plum.service.EmployeeService;
import com.plum.service.OrganisationsService;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/plum")
public class OrganisationInsuranceController {
    @Autowired
    private OrganisationsService organisationsService;

    @Autowired
    private EmployeeService employeeService;

    /**
     * This Controller is used to onboard a new organisation onto our system
     * @param addOrgansisationRequest
     * @return The status response along with the id
     * @throws Exception
     */
    @PostMapping(value = "/organisations", produces = MediaType.APPLICATION_JSON_VALUE)
    public AddOrganisationsResponse addOrganisationsResponse(@NotNull @Valid @RequestBody AddOrganisationRequest addOrgansisationRequest) throws Exception {

        AddOrganisationsResponse addOrganisationsResponse;
        try {
            addOrganisationsResponse = organisationsService.createOrganisationEntry(addOrgansisationRequest);
        } catch (Exception e) {
            throw new Exception("Error in Creating Oragnisation" + Arrays.toString(e.getStackTrace()));
        }
        return addOrganisationsResponse;
    }

    /**
     * Takes CSV as an input, containing information of all the employees
     * @param orgId
     * @param employeeInfoFile
     * @return the list of error entries in the CSV
     */
    @PostMapping(value = "/organisations/{orgId}/members/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public AddEmployeeResponse addEmployeeResponse(@PathVariable("orgId") Integer orgId,
                                                   @RequestParam("employee_csv") MultipartFile employeeInfoFile) {

        List<Integer> errorIndex = new ArrayList<>();
        if (!organisationsService.isOrgPresent(orgId)) {
            return employeeService.addEmployeeResponseNegative();
        }
        List<AddEmployeeRequest> employeeRequestList = employeeService.getDataFromCsv(employeeInfoFile);
        List<AddEmployeeRequest> employeeRequestListValidated = employeeService.validationChecks(employeeRequestList, errorIndex);
        Integer success = employeeRequestListValidated.size();
        employeeService.addEmployee(employeeRequestListValidated, orgId);
        ResponseStatusEnum status;
        if (success == 0)
            status = ResponseStatusEnum.FAILURE;
        else
            status = ResponseStatusEnum.SUCCESS;
        return AddEmployeeResponse
                .builder()
                .status(status)
                .sucessCount(success)
                .failureCount(employeeRequestList.size() - success)
                .errorIndex(errorIndex)
                .build();
    }

    /**
     * Shows the Paginated response the data sorted by org
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/organisations/paginated")
    public PaginatedResponse getOrganisationsData(@RequestParam("page") Integer page,
                                                  @RequestParam("size") Integer size) {
        return employeeService.getPaginatedResponse(page, size);
    }
}
