package com.plum.constants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HelperConstants {

    public static final String SUCCESS_MSG = "successful";
    public static final String FAILED_MSG = "failed";
    public static final String UNPROCESSED_MSG = "Pre-Check failed, check input data";
    public static final List<String> CsvColValidator = Collections.unmodifiableList(Arrays.asList(
            "Employee ID", "First Name", "Middle Name", "Last Name", "Email ID", "Date of Birth", "Gender"));
    public static final String EMAIL_REGEX_EXPRESSION = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
}
