package blank.blank.rep;
import org.springframework.data.jpa.repository.JpaRepository;
import blank.blank.models.UserModel;

public interface UserRep extends JpaRepository<UserModel, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
