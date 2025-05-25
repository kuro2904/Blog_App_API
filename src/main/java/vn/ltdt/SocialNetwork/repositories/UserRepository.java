package vn.ltdt.SocialNetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ltdt.SocialNetwork.models.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
}
