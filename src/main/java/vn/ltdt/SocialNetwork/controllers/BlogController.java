package vn.ltdt.SocialNetwork.controllers;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.ltdt.SocialNetwork.dtos.blog.BlogResponse;
import vn.ltdt.SocialNetwork.services.BlogService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @GetMapping
    public ResponseEntity<Page<BlogResponse>> getBlogs(
            @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
            @RequestParam(value = "sortField", defaultValue = "createdAt") String sortField,
            @RequestParam(value = "sortDirection", defaultValue = "desc") String sortDirection,
            @RequestParam(value = "searchText", required = false) String searchText
    ) {
        return ResponseEntity.ok(
                blogService.findBlogs(pageNum, pageSize, sortField, sortDirection, Optional.ofNullable(searchText))
        );
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createBlog(
            @AuthenticationPrincipal Jwt jwt,
            @RequestPart("visibilityScope") String visibilityScope,
            @RequestPart String content,
            @RequestPart(required = false) List<MultipartFile> images ) {
        blogService.save(jwt,content,visibilityScope,Optional.ofNullable(images));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("my-blog/{userEmail}")
    public ResponseEntity<Page<BlogResponse>> getBlogsByUser(
            @AuthenticationPrincipal Jwt requestUser,
            @PathVariable String userEmail,
            @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        return ResponseEntity.ok(blogService.findBlogByUserEmail(requestUser, userEmail, pageNum, pageSize));
    }

}
