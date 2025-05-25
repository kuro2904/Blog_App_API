package vn.ltdt.SocialNetwork.repositories.images;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ltdt.SocialNetwork.models.image.BlogImage;

import java.util.UUID;

public interface BlogImageRepository extends JpaRepository<BlogImage, UUID> {
}
