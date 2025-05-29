package vn.ltdt.SocialNetwork.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import vn.ltdt.SocialNetwork.models.User;
import vn.ltdt.SocialNetwork.repositories.UserRepository;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final UserRepository userRepository;

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt source) {
        String userId = source.getClaimAsString("sub");
        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Collection<? extends GrantedAuthority> authorities = source.getClaimAsStringList("roles").stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        log.info("User {} has roles {}", userId, authorities);
        return new UsernamePasswordAuthenticationToken(user, source, authorities);
    }
}
