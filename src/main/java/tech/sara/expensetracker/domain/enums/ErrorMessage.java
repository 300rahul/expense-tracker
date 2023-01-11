package tech.sara.expensetracker.domain.enums;

import lombok.Getter;

@Getter
public enum ErrorMessage {
    USER_DOES_NOT_EXIST("user does not exist", 4001),
    FAILED_TO_UPDATE_MONTHLY_INCOME("failed to update monthly income", 4002);
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
