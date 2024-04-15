package cl.duoc.sumativa1.app.partec.service;

import cl.duoc.sumativa1.app.partec.model.Publication;
import cl.duoc.sumativa1.app.partec.repository.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublicationServiceImpl implements PublicationService {

    final PublicationRepository repository;

    @Autowired
    public PublicationServiceImpl(PublicationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Publication> getPublications() {
        return repository.findAll();
    }

    public Optional<Publication> getPublication(Long id) {
        return repository.findById(id);
    }

    @Override
    public Publication addPublication(Publication publication) {
        return repository.save(publication);
    }

    @Override
    public Publication updatePublication(Publication publication) {
        return repository.saveAndFlush(publication);
    }

    @Override
    public void deletePublication(Long id) {
        repository.deleteById(id);
    }

}
