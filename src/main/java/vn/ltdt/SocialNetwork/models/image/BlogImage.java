package vn.ltdt.SocialNetwork.models.image;

import jakarta.persistence.*;
import lombok.*;
import vn.ltdt.SocialNetwork.models.Blog;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BlogImage extends AppImage {
    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;
}
