package vn.ltdt.SocialNetwork.models.comment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import vn.ltdt.SocialNetwork.models.Blog;
import vn.ltdt.SocialNetwork.models.User;
import vn.ltdt.SocialNetwork.models.image.CommentImage;
import vn.ltdt.SocialNetwork.models.reaction.CommentReaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @UuidGenerator
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
    private String content;
    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @CreatedDate
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parent;
    @Enumerated(EnumType.STRING)
    private CommentLevel level;
    @Enumerated(EnumType.STRING)
    private CommentType type;
    @OneToMany(mappedBy = "comment", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CommentReaction> reactions;
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true)
    private List<CommentImage> images;

}
