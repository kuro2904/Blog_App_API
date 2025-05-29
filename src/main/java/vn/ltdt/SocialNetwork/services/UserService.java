package vn.ltdt.SocialNetwork.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.ltdt.SocialNetwork.dtos.auth.RegisterRequest;
import vn.ltdt.SocialNetwork.dtos.user.UserDTO;
import vn.ltdt.SocialNetwork.dtos.user.UserListDTO;
import vn.ltdt.SocialNetwork.exceptions.AlreadyExistedException;
import vn.ltdt.SocialNetwork.exceptions.ContentNotFoundException;
import vn.ltdt.SocialNetwork.models.User;
import vn.ltdt.SocialNetwork.repositories.UserRepository;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Loading user by email: {}", email);
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
                        .fullName(String.format("%s %s", registerRequest.firstName(), registerRequest.lastName()))
                        .build()
        );
    }

    public Page<UserListDTO> findUser(int pageNumber, int pageSize, Optional<String> searchText, Optional<String> sortField) {
        Page<User> users;
        log.info("Finding users by full name {}", searchText.orElse("...") );
        Sort.Direction sortDirectionEnum = Sort.Direction.DESC;
        String sortByField = sortField.orElse("createdAt");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortDirectionEnum, sortByField));
        if ( searchText.isPresent()) {
            users = userRepository.findByFullNameContaining(searchText.get(),pageable);
        }else {
            users = userRepository.findAll(pageable);
        }
        return users.map(UserListDTO::new);
    }

    public UserDTO findByEmail(String email) {
        log.info("Finding user by email {}", email);
        return userRepository.findByEmail(email).map(UserDTO::new).orElseThrow(()-> new ContentNotFoundException("User", "Email",email));
    }

}
