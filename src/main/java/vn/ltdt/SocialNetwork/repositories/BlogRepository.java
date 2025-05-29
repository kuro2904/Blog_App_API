package vn.ltdt.SocialNetwork.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.ltdt.SocialNetwork.models.Blog;
import vn.ltdt.SocialNetwork.models.BlogVisibilityScope;
import vn.ltdt.SocialNetwork.models.User;

import java.util.List;
import java.util.UUID;

public interface BlogRepository extends JpaRepository<Blog, UUID> {
    Page<Blog> findByContentContaining(String searchText, Pageable pageable);
    Page<Blog> findByAuthor(User author, Pageable pageable);
    Page<Blog> findByAuthorAndVisibilityScopeIn(User author, List<BlogVisibilityScope> visibilityScopes, Pageable pageable);
}
