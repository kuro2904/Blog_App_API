package vn.ltdt.SocialNetwork.repositories.images;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ltdt.SocialNetwork.models.image.CommentImage;

import java.util.UUID;

public interface CommentImageRepository extends JpaRepository<CommentImage, UUID> {
}
