package vn.ltdt.SocialNetwork.dtos;

import vn.ltdt.SocialNetwork.models.image.AppImage;

public record ImageDTO(
        String id,
        String url
) {
    public ImageDTO(AppImage appImage) {
        this(
                appImage.getId().toString(),
                appImage.getUrl()
        );
    }
}
