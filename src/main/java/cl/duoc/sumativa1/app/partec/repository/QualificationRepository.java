package cl.duoc.sumativa1.app.partec.repository;

import cl.duoc.sumativa1.app.partec.model.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QualificationRepository extends JpaRepository<Qualification, Long> {

    List<Qualification> findByIdPublication(Long idPublication);
}
