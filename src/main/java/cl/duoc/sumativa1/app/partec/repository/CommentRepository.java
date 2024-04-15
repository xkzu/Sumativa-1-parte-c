package cl.duoc.sumativa1.app.partec.repository;

import cl.duoc.sumativa1.app.partec.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByIdPublication(Long idPublication);
}
