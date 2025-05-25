package vn.ltdt.SocialNetwork.exceptions;

import lombok.Getter;

@Getter
public class AlreadyExistedException extends RuntimeException {
    private final String dataType;
    private final String field;
    private final String value;

    public AlreadyExistedException(String dataType, String field, String value) {
        super(String.format("%s already existed with %s: %s", dataType, field, value));
        this.dataType = dataType;
        this.field = field;
        this.value = value;
    }
}
