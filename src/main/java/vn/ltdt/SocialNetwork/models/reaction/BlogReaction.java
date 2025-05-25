package vn.ltdt.SocialNetwork.models.reaction;

import jakarta.persistence.*;
import lombok.*;
import vn.ltdt.SocialNetwork.models.Blog;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BlogReaction extends AppReaction {
    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;
}
