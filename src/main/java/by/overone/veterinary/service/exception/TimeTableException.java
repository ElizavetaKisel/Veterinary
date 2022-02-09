package by.overone.veterinary.service.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class TimeTableException extends RuntimeException{
    private ExceptionCode code;
    public TimeTableException(ExceptionCode code) {
        this.code = code;
    }
}
