package vn.ltdt.SocialNetwork.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.ltdt.SocialNetwork.dtos.auth.RegisterRequest;
import vn.ltdt.SocialNetwork.exceptions.AlreadyExistedException;
import vn.ltdt.SocialNetwork.models.User;
import vn.ltdt.SocialNetwork.repositories.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }

    public User createUser(RegisterRequest registerRequest) {
        Optional<User> existedEmail = userRepository.findByEmail(registerRequest.email());
        if (existedEmail.isPresent()) {
            throw new AlreadyExistedException("User","email", registerRequest.email());
        }
        Optional<User> existedPhoneNumber = userRepository.findByPhoneNumber(registerRequest.phoneNumber());
        if (existedPhoneNumber.isPresent()) {
            throw new AlreadyExistedException("User","phone", registerRequest.phoneNumber());
        }
        return userRepository.save(
                User.builder()
                        .email(registerRequest.email())
                        .phoneNumber(registerRequest.phoneNumber())
                        .password(passwordEncoder.encode(registerRequest.password()))
                        .firstName(registerRequest.firstName())
                        .lastName(registerRequest.lastName())
                        .build()
        );
    }
}
