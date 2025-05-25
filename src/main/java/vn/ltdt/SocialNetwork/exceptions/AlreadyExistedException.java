package vn.ltdt.SocialNetwork.exceptions;

public class AlreadyExistedException extends RuntimeException {

    private String dataType;
    private String field;
    private String value;

    public AlreadyExistedException(String dataType, String field, String value) {
        super(String.format("%s already existed with %s: %s", dataType, field, value));
    }
}
