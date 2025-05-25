package vn.ltdt.SocialNetwork.dtos;

import vn.ltdt.SocialNetwork.models.Blog;
import vn.ltdt.SocialNetwork.models.image.AppImage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record BlogDTO(
        UUID id,
        String content,
        String author,
        LocalDateTime createAt,
        List<String> images,
        List<ReactionDTO> reactions
) {

    public BlogDTO(Blog blog) {
       this (
        blog.getId(),
        blog.getContent(),
        blog.getAuthor().getFirstName(),
        blog.getCreatedAt(),
        blog.getAppImage().stream().map(AppImage::getUrl).toList(),
        blog.getReactions().stream().map(ReactionDTO::new).toList()
       );
    }
}
