package vn.ltdt.SocialNetwork.dtos.blog;

import vn.ltdt.SocialNetwork.dtos.ReactionDTO;
import vn.ltdt.SocialNetwork.models.Blog;
import vn.ltdt.SocialNetwork.models.image.AppImage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record BlogResponse(
        UUID id,
        String content,
        String author,
        LocalDateTime createAt,
        List<String> images,
        List<ReactionDTO> reactions
) {

    public BlogResponse(Blog blog) {
       this (
        blog.getId(),
        blog.getContent(),
        blog.getAuthor().getFirstName(),
        blog.getCreatedAt(),
        blog.getImages().stream().map(AppImage::getUrl).toList(),
        blog.getReactions().stream().map(ReactionDTO::new).toList()
       );
    }
}
