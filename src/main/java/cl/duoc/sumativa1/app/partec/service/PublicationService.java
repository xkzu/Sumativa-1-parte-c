package cl.duoc.sumativa1.app.partec.service;

import cl.duoc.sumativa1.app.partec.model.Publication;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PublicationService {

    List<Publication> getPublications();

    Optional<Publication> getPublication(Long id);

    Map<String, String> getAverage(Long id);
}
