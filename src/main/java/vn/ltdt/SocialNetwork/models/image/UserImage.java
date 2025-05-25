package vn.ltdt.SocialNetwork.models.image;

import jakarta.persistence.*;
import lombok.*;
import vn.ltdt.SocialNetwork.models.User;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class UserImage extends AppImage{
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
