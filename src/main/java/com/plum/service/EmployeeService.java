package com.plum.service;

import com.opencsv.CSVReader;
import com.plum.constants.HelperConstants;
import com.plum.entity.EmployeeEntity;
import com.plum.entity.PaginatedEntity;
import com.plum.enums.GenderEnum;
import com.plum.enums.ResponseStatusEnum;
import com.plum.repo.EmployeeRepo;
import com.plum.request.AddEmployeeRequest;
import com.plum.response.AddEmployeeResponse;
import com.plum.response.PaginatedResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    /**
     * This method reads data from the csv and checks which header is present
     * in which column before gathering data from CSV
     *
     * @param employeeInfoFile
     * @return returns the list of employee request
     */
    public List<AddEmployeeRequest> getDataFromCsv(MultipartFile employeeInfoFile) {
        try (Reader reader = new InputStreamReader(employeeInfoFile.getInputStream())) {
            CSVReader csvReader = new CSVReader(reader);
            String[] headers = csvReader.readNext();
            Set<String> headerSet = new LinkedHashSet<>(Arrays.asList(headers));

            String[] cols;
            List<AddEmployeeRequest> addEmployeeRequestList = new ArrayList<>();

            // Reading CSV line by line
            while ((cols = csvReader.readNext()) != null) {
                Map<String, String> rowEntry = new HashMap<>();

                // To Check which header is in which column
                for (int i = 0; i < headers.length; i++) {
                    if (HelperConstants.CsvColValidator.contains(headers[i])) {
                        rowEntry.put(headers[i], cols[i].trim());
                    }
                }
                AddEmployeeRequest addEmployeeRequest = AddEmployeeRequest
                        .builder()
                        .employeeId(Integer.parseInt(rowEntry.get(HelperConstants.CsvColValidator.get(0))))
                        .firstName(rowEntry.get(HelperConstants.CsvColValidator.get(1)))
                        .middleName(rowEntry.get(HelperConstants.CsvColValidator.get(2)))
                        .lastName(rowEntry.get(HelperConstants.CsvColValidator.get(3)))
                        .emailID(rowEntry.get(HelperConstants.CsvColValidator.get(4)))
                        .dateOfBirth(rowEntry.get(HelperConstants.CsvColValidator.get(5)))
                        .gender(rowEntry.get(HelperConstants.CsvColValidator.get(6)))
                        .build();
                addEmployeeRequestList.add(addEmployeeRequest);
            }
            return addEmployeeRequestList;

        } catch (Exception e) {
            return Collections.emptyList();
        }

    }

    /**
     * Performs all the validation checks given in the problem statement
     *
     * @param employeeRequestList
     * @param errorIndex
     * @return list of employees which pass the validations
     */
    public List<AddEmployeeRequest> validationChecks(List<AddEmployeeRequest> employeeRequestList, List<Integer> errorIndex) {
        if (employeeRequestList.isEmpty())
            return Collections.emptyList();

        List<AddEmployeeRequest> addEmployeeRequestList = new ArrayList<>();
        int counter = 0;
        for (AddEmployeeRequest employee : employeeRequestList) {
            Integer employeeId = employee.getEmployeeId();
            counter++;
            List<EmployeeEntity> employeePresent = employeeRepo.findAllById(employeeId);
            if (!CollectionUtils.isEmpty(employeePresent) || !emailCheck(employee.getEmailID()) || !nameCheck(
                    employee.getFirstName()) || !nameCheck(employee.getLastName()) || !dobCheck(
                    employee.getDateOfBirth()) || !genderCheck(employee.getGender())) {
                errorIndex.add(counter);
            } else
                addEmployeeRequestList.add(employee);
        }
        return addEmployeeRequestList;
    }

    public void addEmployee(List<AddEmployeeRequest> employeeList, Integer orgId) {
        for (AddEmployeeRequest employee : employeeList) {
            EmployeeEntity employeeEntity = new EmployeeEntity();
            employeeEntity.setId(employee.getEmployeeId());
            employeeEntity.setEmailID(employee.getEmailID());
            employeeEntity.setFirstName(employee.getFirstName());
            employeeEntity.setGender(employee.getGender());
            employeeEntity.setLastName(employee.getLastName());
            employeeEntity.setMiddleName(employee.getMiddleName());
            employeeEntity.setOrgId(orgId);
            employeeEntity.setDateOfBirth(employee.getDateOfBirth());

            employeeRepo.save(employeeEntity);
        }
    }

    public Boolean emailCheck(String emailId) {
        if (StringUtils.isBlank(emailId))
            return true;
        emailId = emailId.trim();
        return Pattern.compile(HelperConstants.EMAIL_REGEX_EXPRESSION)
                .matcher(emailId)
                .matches();
    }

    public Boolean nameCheck(String name) {
        if (StringUtils.isBlank(name))
            return false;
        name = name.trim();
        return name.length() > 3 && name.chars().allMatch(Character::isLetter);
    }

    public Boolean dobCheck(String dob) {
        if (StringUtils.isBlank(dob))
            return false;
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(dob);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public Boolean genderCheck(String gender) {
        if (StringUtils.isBlank(gender))
            return false;
        GenderEnum name = GenderEnum.getGenderByName(gender);
        return !Objects.isNull(name);
    }

    /**
     * gives a failed response status in response
     *
     * @return
     */
    public AddEmployeeResponse addEmployeeResponseNegative() {
        return AddEmployeeResponse
                .builder()
                .status(ResponseStatusEnum.FAILURE)
                .build();
    }

    public PaginatedResponse getPaginatedResponse(Integer pageNumber, Integer pageSize) {
        Pageable sortedByOrg = PageRequest.of(pageNumber, pageSize, Sort.by("orgId").ascending());
        Page<EmployeeEntity> page = employeeRepo.findAll(sortedByOrg);
        List<EmployeeEntity> employeeEntityList = page.getContent();
        Map<Integer, List<EmployeeEntity>> orgEmployees = sortByOrg(employeeEntityList);
        List<PaginatedEntity> paginatedEnitityList = new ArrayList<>();
        orgEmployees.keySet().forEach(orgId -> {
            PaginatedEntity paginatedEntity = new PaginatedEntity();
            List<EmployeeEntity> employeeEntities = orgEmployees.get(orgId);
            paginatedEntity.setOrgId(orgId);
            paginatedEntity.setEmployeeCount(employeeEntities.size());
            paginatedEntity.setEmployeeDetails(employeeEntities);
            paginatedEnitityList.add(paginatedEntity);
        });
        return PaginatedResponse
                .builder()
                .noOfEmployees(employeeEntityList.size())
                .employeesByOrg(paginatedEnitityList)
                .build();
    }

    /**
     * The input list already contains the org id and employee entity.
     * we convert this to a map of orgId and list of all the employee's in that org
     *
     * @param employeeEntitityList
     * @return
     */
    public Map<Integer, List<EmployeeEntity>> sortByOrg(List<EmployeeEntity> employeeEntitityList) {
        Map<Integer, List<EmployeeEntity>> orgEmployeeMap = new HashMap<>();
        employeeEntitityList.forEach(employeeEntity -> {
            List<EmployeeEntity> list = orgEmployeeMap.get(employeeEntity.getOrgId());
            if (Objects.isNull(list))
                list = new ArrayList<>();
            list.add(employeeEntity);
            orgEmployeeMap.put(employeeEntity.getOrgId(), list);
        });
        return orgEmployeeMap;
    }

}
