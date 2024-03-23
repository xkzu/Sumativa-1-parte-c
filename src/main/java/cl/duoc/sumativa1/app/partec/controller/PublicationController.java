package cl.duoc.sumativa1.app.partec.controller;

import cl.duoc.sumativa1.app.partec.model.Publishing;
import cl.duoc.sumativa1.app.partec.service.Publication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PublicationController {

    private final Publication publication;

    @Autowired
    public PublicationController(Publication publication) {
        this.publication = publication;
    }

    /* Considerar un microservicio que permita manipular publicaciones con sus respectivoscomentarios y
        calificaciones. Se deben considerar las respectivas validaciones para los
        datos que ingresarán y la generación de promedios de calificaciones de las publicaciones.
    */
    @GetMapping("/publications")
    public ResponseEntity<List<Publishing>> publications() {
        return ResponseEntity.ok(publication.getPublications());
    }

    @GetMapping("/publications/{id}")
    public ResponseEntity<Publishing> getPublication(@PathVariable int id) {
        return ResponseEntity.ok(publication.getPublication(id));
    }
}
