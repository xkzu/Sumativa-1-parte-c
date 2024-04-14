package cl.duoc.sumativa1.app.partec.repository;

import cl.duoc.sumativa1.app.partec.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {
}
