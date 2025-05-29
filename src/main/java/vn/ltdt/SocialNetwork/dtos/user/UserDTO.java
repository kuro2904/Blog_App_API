package vn.ltdt.SocialNetwork.dtos.user;

import vn.ltdt.SocialNetwork.models.User;
import vn.ltdt.SocialNetwork.utilities.DateTimeUtil;

public record UserDTO(
        String id,
        String avatar,
        String fullName,
        String email,
        String firstName,
        String lastName,
        String phoneNumber,
        String createdAt
) {
    public UserDTO(User user) {
        this (
                user.getId().toString(),
                user.getAvatar() != null ? user.getAvatar().getUrl() : null,
                user.getFullName(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                DateTimeUtil.formatDateTime(user.getCreatedAt(),"dd/MM/yyyy")
        );
    }
}
