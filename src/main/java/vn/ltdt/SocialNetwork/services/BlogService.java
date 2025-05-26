package vn.ltdt.SocialNetwork.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ltdt.SocialNetwork.dtos.blog.BlogRequest;
import vn.ltdt.SocialNetwork.dtos.blog.BlogResponse;
import vn.ltdt.SocialNetwork.models.Blog;
import vn.ltdt.SocialNetwork.models.User;
import vn.ltdt.SocialNetwork.repositories.BlogRepository;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

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

    public void save(User author, BlogRequest request) {
        if(request.images() != null && !request.images().isEmpty()){
            log.info("Saving blog images");
        }
        blogRepository.save(
                Blog.builder()
                        .author(author)
                        .content(request.content())
                        .build()
        );
    }
}
