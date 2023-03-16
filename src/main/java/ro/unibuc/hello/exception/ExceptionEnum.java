package ro.unibuc.hello.exception;

import lombok.Getter;

@Getter
public enum ExceptionEnum implements ErrorDispatcher {
    EMPTY_FIELD("Some of the fields were incomplete","error_empty_field"),
    INVALID_FIELD("One or more fields are invalid", "error_invalid_id"),
    OBJECT_NOT_FOUND("The object couldn't be found", "error_object_not_found");

    ExceptionEnum(String error_message, String error_key) {
        this.errorMessage = error_message;
        this.errorKey = error_key;
    }

    private String errorMessage;
    private String errorKey;
}
