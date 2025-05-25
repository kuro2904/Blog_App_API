package vn.ltdt.SocialNetwork.models.image;

import jakarta.persistence.*;
import lombok.*;
import vn.ltdt.SocialNetwork.models.comment.Comment;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)public class CommentImage extends AppImage {
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
