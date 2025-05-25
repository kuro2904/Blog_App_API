package vn.ltdt.SocialNetwork.dtos;

import org.springframework.http.HttpStatus;

public record ErrorDTO(
        String path,
        HttpStatus httpStatus,
        String message
) {
}
