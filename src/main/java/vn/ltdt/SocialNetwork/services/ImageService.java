package vn.ltdt.SocialNetwork.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.ltdt.SocialNetwork.models.image.ImageType;
import vn.ltdt.SocialNetwork.repositories.images.BlogImageRepository;
import vn.ltdt.SocialNetwork.repositories.images.CommentImageRepository;
import vn.ltdt.SocialNetwork.repositories.images.UserImageRepository;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import static vn.ltdt.SocialNetwork.config.Constants.SUPPORTED_IMAGE_TYPES;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {
    private final BlogImageRepository blogImageRepository;
    private final CommentImageRepository commentImageRepository;
    private final UserImageRepository userImageRepository;
    private final GoogleDriveService googleDriveService;
    private static final String IMAGE_FOLDER = "/images";

    public void uploadImage(List<MultipartFile> images, ImageType imageType) throws GeneralSecurityException, IOException {
        if (images == null || images.isEmpty()) {
            throw new IllegalArgumentException("No images provided");
        }
        if (images.stream().anyMatch(image -> !SUPPORTED_IMAGE_TYPES.contains(image.getContentType()))) {
            throw new IllegalArgumentException("One or more images have unsupported types.");
        }
        switch (imageType) {
            case IMAGE_BLOG -> {
                log.info("Upload Blog Image");
                googleDriveService.getInstance().drives().create(String.valueOf("%s/&s",IMAGE_FOLDER, "filename"));
                break;
            }
            case IMAGE_COMMENT -> {
                log.info("Upload Comment Image");
                break;
            }
            case IMAGE_USER -> {
                log.info("Upload User Image");
                break;
            }
            default -> {
                log.info("Unknown image type");
                break;
            }
        }
    }
}

