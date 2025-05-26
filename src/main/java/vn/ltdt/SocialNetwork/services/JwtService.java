package vn.ltdt.SocialNetwork.services;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.ltdt.SocialNetwork.dtos.auth.JWTToken;
import vn.ltdt.SocialNetwork.models.User;

import java.time.Instant;
import java.util.Date;

@Service
@Slf4j
public class JwtService {

    @Value("${app.secretKey}")
    private String SECRET_KEY;

    public JWTToken generateToken(User user) {
        try{
            log.info("Generating token");
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(user.getId().toString())
                    .issueTime(new Date())
                    .expirationTime(Date.from(Instant.now().plusSeconds(3600 * 24 * 30))) // 1 month
                    .claim("roles", user.getAuthorities())
                    .claim("userFullName", String.format("%s %s",user.getFirstName(), user.getLastName()))
                    .claim("imageUrl",user.getAvatar() == null ? null : user.getAvatar().getUrl())
                    .claim("email",user.getEmail())
                    .build();

            JWSSigner signer = new MACSigner(SECRET_KEY);
            JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256),
                    claimsSet.toPayload());
            jwsObject.sign(signer);
            return new JWTToken("Bearer",jwsObject.serialize());
        } catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }


}
