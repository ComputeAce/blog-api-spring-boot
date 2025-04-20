package blank.blank.rep;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import blank.blank.models.PostModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PostRep extends JpaRepository<PostModel, Long> {
    @Query("SELECT p FROM PostModel p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<PostModel> findByTitleContainingIgnoreCase(@Param("title") String title);
    
}


