package br.com.project.portfolio.rest.api.shared.util;

public class Constants {

    public static final String GENERIC_ERROR = "An unexpected error occurred.";
    public static final String TOTAL_MAX_EXCEEDED = "Maximum number of members exceeded.";
    public static final String MEMBER_ERROR = "Member not found. ID: ";
    public static final String MEMBER_FOUND_IN_PROJECT = "Member is already assigned to this project.";
    public static final String MEMBER_NOT_FOUND = "Member not found.";
    public static final String PROJECT_NOT_FOUND = "Project not found.";
    public static final String PROJECT_STATUS_NOT_PERMITTED = "Project status not allowed.";
    public static final String OCCUPATION_ERROR = "Occupation not allowed.";
    public static final String OCCUPATION_ERROR_IS_NULL = "Occupation is required.";
    public static final String CONTRACT_ERROR = "Employment contract not allowed.";
    public static final String CONTRACT_ERROR_IS_NULL = "Employment contract is required.";
    public static final String GENERIC_SUCCESS_MESSAGE = "Operation completed successfully.";

    public static final String[] SWAGGER_WHITELIST = {"/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"};

    public static final String[] ACTUATOR_WHITELIST = {"/actuator/health", "/actuator/info"};

    public static final String[] PUBLIC_MISC = {"/error"};


}
