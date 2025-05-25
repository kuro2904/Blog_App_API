package vn.ltdt.SocialNetwork.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vn.ltdt.SocialNetwork.models.image.ImageType;
import vn.ltdt.SocialNetwork.services.ImageService;

import java.util.List;


@RestController
@RequestMapping("images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping(value = "upload",
            consumes = {"multipart/form-data"})
    public ResponseEntity<Void> uploadImage(@RequestParam("images") List<MultipartFile> files, @RequestParam("type") String type) {
        return ResponseEntity.ok().build();
    }
}
