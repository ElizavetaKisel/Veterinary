package by.overone.veterinary.service.exception;

public enum ExceptionCode {
    NOT_EXISTING_USER ("11111"),
    NOT_EXISTING_PET ("11112"),
    NOT_EXISTING_APPOINTMENT ("11113"),
    NOT_EXISTING_DOCTOR ("11114"),
    ALREADY_EXISTING_USER ("22221"),
    ALREADY_EXISTING_PET ("22222"),
    ALREADY_EXISTING_APPOINTMENT ("22223"),
    WRONG_DATE ("99991"),
    WRONG_TIME ("99992");
    private final String errorCode;

    ExceptionCode(String errorCode){
        this.errorCode = errorCode;
    }

    public String getErrorCode(){
        return errorCode;
    }
}
