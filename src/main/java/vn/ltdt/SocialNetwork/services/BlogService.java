package vn.ltdt.SocialNetwork.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vn.ltdt.SocialNetwork.dtos.blog.BlogResponse;
import vn.ltdt.SocialNetwork.models.Blog;
import vn.ltdt.SocialNetwork.models.BlogVisibilityScope;
import vn.ltdt.SocialNetwork.models.User;
import vn.ltdt.SocialNetwork.models.image.BlogImage;
import vn.ltdt.SocialNetwork.repositories.BlogRepository;
import vn.ltdt.SocialNetwork.repositories.images.BlogImageRepository;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final BlogImageRepository blogImageRepository;
    private final GoogleDriveService googleDriveService;

    public Page<BlogResponse> fetch(int pageNum, int pageSize, String sortField, String sortDirection, String searchText) {
        Sort.Direction sortDirectionEnum = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(pageNum, pageSize, sortDirectionEnum, sortField);
        Page<BlogResponse> result;

        if(searchText == null || searchText.isBlank())
            result =  blogRepository.findAll(pageable).map(BlogResponse::new);
        else
            result = blogRepository.findByContentContaining(searchText, pageable).map(BlogResponse::new);

        return result;
    }

    public void save(User author, String content, String visibilityScopeStr, List<MultipartFile> images) {
        BlogVisibilityScope visibilityScope;
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
        List<BlogImage> blogImages = blogImageRepository.saveAll(googleDriveService.uploadMultipleFiles(images).stream().map(link -> {
            BlogImage blogImage = new BlogImage();
            blogImage.setUrl(link);
            blogImage.setBlog(blog);
            return blogImage;
        }).toList());
        blog.setImages(blogImages);
        blogRepository.save(blog);
    }
}
