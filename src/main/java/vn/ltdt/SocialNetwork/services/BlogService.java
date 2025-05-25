package vn.ltdt.SocialNetwork.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.ltdt.SocialNetwork.dtos.BlogDTO;
import vn.ltdt.SocialNetwork.models.Blog;
import vn.ltdt.SocialNetwork.models.image.BlogImage;
import vn.ltdt.SocialNetwork.repositories.BlogRepository;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    public Page<BlogDTO> fetch(int pageNum, int pageSize, String sortField, String sortDirection, String searchText) {
        Sort.Direction sortDirectionEnum = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(pageNum, pageSize, sortDirectionEnum, sortField);
        Page<BlogDTO> result;

        if(searchText == null || searchText.isBlank())
            result =  blogRepository.findAll(pageable).map(BlogDTO::new);
        else
            result = blogRepository.findByContentContaining(searchText, pageable).map(BlogDTO::new);

        return result;
    }

    public void save(BlogDTO blogDTO) {
        List<BlogImage> images = new ArrayList<>();

        blogRepository.save(
                Blog.builder()
                        .build()
        );
    }
}
