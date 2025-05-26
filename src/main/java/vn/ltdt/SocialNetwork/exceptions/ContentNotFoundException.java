package vn.ltdt.SocialNetwork.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentNotFoundException extends RuntimeException {

    private final String dataType;
    private final String field;
    private final String value;

    public ContentNotFoundException(String dataType, String field, String value) {
        super(String.format("Can not find %s in %s with %s",dataType, field, value));
        this.dataType = dataType;
        this.field = field;
        this.value = value;
    }
}
