package by.overone.veterinary.exception;

public enum ExceptionCode {
    NOT_EXISTING_USER ("0001"),
    NOT_EXISTING_PET ("0002");
    private final String errorCode;

    ExceptionCode(String errorCode){
        this.errorCode = errorCode;
    }

    public String getErrorCode(){
        return errorCode;
    }
}
