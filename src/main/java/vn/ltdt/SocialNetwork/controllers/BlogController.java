package vn.ltdt.SocialNetwork.controllers;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.ltdt.SocialNetwork.dtos.BlogDTO;
import vn.ltdt.SocialNetwork.services.BlogService;

@RestController
@RequestMapping("blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    public ResponseEntity<Page<BlogDTO>> getBlogs(
            @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
            @RequestParam(value = "sortFiled", required = false) String sortField,
            @RequestParam(value = "sortDirection", required = false) String sortDirection,
            @RequestParam(value = "searchText", required = false) String searchText
    ) {
        return ResponseEntity.ok(blogService.fetch(pageNum, pageSize, sortField, sortDirection, searchText));
    }
}
