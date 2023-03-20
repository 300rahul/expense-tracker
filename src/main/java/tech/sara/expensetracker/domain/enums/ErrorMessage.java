package tech.sara.expensetracker.domain.enums;

import lombok.Getter;

@Getter
public enum ErrorMessage {
    USER_DOES_NOT_EXIST("user does not exist", 4001),
    FAILED_TO_UPDATE_MONTHLY_INCOME("failed to update monthly income", 4002),
    FAILED_TO_ADD_DAILY_EXPENSE("failed to add daily expense", 4003),
    FAILED_PROCESS_REQUEST("Failed to process request.", 4004),
    INVALID_USER("Invalid user.", 4005),
    REQUEST_VALIDATION_FAIL("Invalid request", 4006);
    private String message;
    private Integer code;

    ErrorMessage(String message, int code) {
        this.message = message;
        this.code = code;
    }


    public String getMessage() {
        return this.message;
    }
    public int getCode(){
        return this.code;
    }
}
