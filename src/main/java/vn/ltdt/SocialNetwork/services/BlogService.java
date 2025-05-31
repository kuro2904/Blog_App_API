package vn.ltdt.SocialNetwork.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vn.ltdt.SocialNetwork.dtos.blog.BlogResponse;
import vn.ltdt.SocialNetwork.exceptions.ContentNotFoundException;
import vn.ltdt.SocialNetwork.models.Blog;
import vn.ltdt.SocialNetwork.models.BlogVisibilityScope;
import vn.ltdt.SocialNetwork.models.User;
import vn.ltdt.SocialNetwork.models.image.BlogImage;
import vn.ltdt.SocialNetwork.repositories.BlogRepository;
import vn.ltdt.SocialNetwork.repositories.UserRepository;
import vn.ltdt.SocialNetwork.repositories.images.BlogImageRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final BlogImageRepository blogImageRepository;
    private final AWSService awsService;
    private final UserRepository userRepository;

    public Page<BlogResponse> findBlogs(int pageNum, int pageSize, String sortField, String sortDirection, Optional<String> searchText) {
        Sort.Direction sortDirectionEnum = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(pageNum, pageSize, sortDirectionEnum, sortField);
        return searchText.map(s -> blogRepository.findByContentContaining(s, pageable).map(BlogResponse::new)).orElseGet(
                () -> blogRepository.findAll(pageable).map(BlogResponse::new)
        );
    }

    public void save(Jwt jwt, String content, String visibilityScopeStr, Optional<List<MultipartFile>> images) {
        BlogVisibilityScope visibilityScope;
        User author = userRepository.findById(UUID.fromString(jwt.getSubject())).orElseThrow(
                () -> new ContentNotFoundException("User","Id", jwt.getId())
        );
        try {
            visibilityScope = BlogVisibilityScope.valueOf(visibilityScopeStr);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid visibility scope: " + visibilityScopeStr);
        }
        Blog blog = blogRepository.save(
                Blog.builder()
                        .author(author)
                        .content(content)
                        .visibilityScope(visibilityScope)
                        .build()
        );

        if(images.isPresent()){
            List<BlogImage> imgUrl = images.get().stream().map(awsService::upload).map(link -> {
                        BlogImage blogImage = new BlogImage();
                        blogImage.setUrl(link);
                        blogImage.setBlog(blog);
                        return blogImage;
                    }
            ).toList();
            List<BlogImage> blogImages = blogImageRepository.saveAll(imgUrl);
            blog.setImages(blogImages);
        }
        blogRepository.save(blog);
    }

    public Page<BlogResponse> findBlogByUserEmail(Jwt requestUser, String userEmail, int pageNum, int pageSize) {
        User author = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new ContentNotFoundException("User","Email", userEmail)
        );
        Sort.Direction sortDirectionEnum = Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(pageNum, pageSize, sortDirectionEnum, "createdAt");
        Page<Blog> blogs;
        if(requestUser.getSubject().equals(author.getId().toString())) {
            blogs = blogRepository.findByAuthor(author,pageable);
        }
        else {
            blogs = blogRepository.findByAuthorAndVisibilityScopeIn(
                    author,
                    List.of(BlogVisibilityScope.PUBLIC, BlogVisibilityScope.FRIENDS),
                    pageable);
        }
        return blogs.map(BlogResponse::new);
    }
}
