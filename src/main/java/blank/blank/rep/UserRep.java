package blank.blank.rep;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import blank.blank.models.UserModel;

public interface UserRep extends JpaRepository<UserModel, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<UserModel> findByEmail(String email);
}
