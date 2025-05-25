package vn.ltdt.SocialNetwork.models.reaction;

import jakarta.persistence.*;
import lombok.Getter;
import vn.ltdt.SocialNetwork.models.User;

import java.util.UUID;

@MappedSuperclass
@Getter
public abstract class AppReaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Enumerated(EnumType.STRING)
    private ReactionType type;
    @ManyToOne(fetch = FetchType.LAZY)
    private User reactedBy;
}
