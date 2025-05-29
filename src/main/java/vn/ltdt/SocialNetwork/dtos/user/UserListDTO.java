package vn.ltdt.SocialNetwork.dtos.user;

import vn.ltdt.SocialNetwork.models.User;

public record UserListDTO(
        String id,
        String avatar,
        String fullName,
        String email
) {
    public UserListDTO(User user) {
        this (
                user.getId().toString(),
                user.getAvatar() != null ? user.getAvatar().getUrl() : null,
                user.getFullName(),
                user.getEmail()
        );
    }
}
