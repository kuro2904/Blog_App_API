package vn.ltdt.SocialNetwork.controllers;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.ltdt.SocialNetwork.dtos.blog.BlogResponse;
import vn.ltdt.SocialNetwork.models.User;
import vn.ltdt.SocialNetwork.services.BlogService;

import java.util.List;

@RestController
@RequestMapping("blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @GetMapping
    public ResponseEntity<Page<BlogResponse>> getBlogs(
            @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
            @RequestParam(value = "sortFiled", defaultValue = "createdAt", required = false) String sortField,
            @RequestParam(value = "sortDirection", defaultValue = "desc", required = false) String sortDirection,
            @RequestParam(value = "searchText", required = false) String searchText
    ) {
        return ResponseEntity.ok(blogService.fetch(pageNum, pageSize, sortField, sortDirection, searchText));
    }

    @PostMapping
    public ResponseEntity<Void> createBlog(@AuthenticationPrincipal User user, @RequestPart String visibilityScope, @RequestPart String content, @RequestPart List<MultipartFile> images ) {
        blogService.save(user,content,visibilityScope,images);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
