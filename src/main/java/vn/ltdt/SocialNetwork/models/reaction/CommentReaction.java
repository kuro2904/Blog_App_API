package vn.ltdt.SocialNetwork.models.reaction;

import jakarta.persistence.*;
import lombok.*;
import vn.ltdt.SocialNetwork.models.comment.Comment;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class CommentReaction extends AppReaction {
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
