package vn.ltdt.SocialNetwork.dtos;

import vn.ltdt.SocialNetwork.models.reaction.AppReaction;

import java.util.UUID;

public record ReactionDTO(
        UUID id,
        String type,
        String reactedBy
) {
    public ReactionDTO(AppReaction reaction){
        this (
                reaction.getId(),
               reaction.getType().name(),
               reaction.getReactedBy().getFirstName()
        );
    }
}
