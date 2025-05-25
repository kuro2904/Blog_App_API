package vn.ltdt.SocialNetwork.dtos.auth;

public record JWTToken(
        String type,
        String token
) {
}
