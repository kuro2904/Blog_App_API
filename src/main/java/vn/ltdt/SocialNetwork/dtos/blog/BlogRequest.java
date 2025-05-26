package vn.ltdt.SocialNetwork.dtos.blog;

import java.util.List;

public record BlogRequest(
                          String content,
                          List<String> images
) {
}
