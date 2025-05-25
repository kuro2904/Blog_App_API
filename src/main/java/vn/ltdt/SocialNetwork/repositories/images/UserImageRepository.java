package vn.ltdt.SocialNetwork.repositories.images;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ltdt.SocialNetwork.models.image.UserImage;

import java.util.UUID;

public interface UserImageRepository extends JpaRepository<UserImage, UUID> {
}
