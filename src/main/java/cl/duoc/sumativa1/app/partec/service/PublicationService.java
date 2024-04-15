package cl.duoc.sumativa1.app.partec.service;

import cl.duoc.sumativa1.app.partec.model.Publication;

import java.util.List;
import java.util.Optional;

public interface PublicationService {

    List<Publication> getPublications();

    Optional<Publication> getPublication(Long id);

    Publication addPublication(Publication publication);
}
