package cl.duoc.sumativa1.app.partec.repository;

import cl.duoc.sumativa1.app.partec.model.Publication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PublicationRepositoryTest {

    private final PublicationRepository publicationRepository;

    @Autowired
    PublicationRepositoryTest(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    @Test
    void findAll() {
        List<Publication> publications = publicationRepository.findAll();
        assertNotNull(publications);
        assertFalse(publications.isEmpty());
    }

    @Test
    void findById() {
        Optional<Publication> publication = publicationRepository.findById(1L);
        assertNotNull(publication);
        assertFalse(publication.isEmpty());
    }

    @Test
    void save() {
        Publication publication = new Publication();
        publication.setTitle("Title");
        publication.setUser("user");
        publication.setContent("content");
        Publication result = publicationRepository.save(publication);
        assertNotNull(result);
        assertEquals(publication.getTitle(), result.getTitle());
        assertEquals(publication.getUser(), result.getUser());
        assertEquals(publication.getContent(), result.getContent());
    }

    @Test
    void delete() {
        Optional<Publication> publication = publicationRepository.findById(1L);
        assertNotNull(publication);
        publicationRepository.delete(publication.get());
        Optional<Publication> result = publicationRepository.findById(1L);
        assertFalse(result.isPresent());
    }

}