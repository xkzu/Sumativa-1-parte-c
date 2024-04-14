package cl.duoc.sumativa1.app.partec.controller;

import cl.duoc.sumativa1.app.partec.model.Publication;
import cl.duoc.sumativa1.app.partec.model.PublicationResponse;
import cl.duoc.sumativa1.app.partec.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class PublicationController {

    private final PublicationService publicationService;

    @Autowired
    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    /* Considerar un microservicio que permita manipular publicaciones con sus respectivoscomentarios y
        calificaciones. Se deben considerar las respectivas validaciones para los
        datos que ingresarán y la generación de promedios de calificaciones de las publicaciones.
    */
    @GetMapping("/publications")
    public ResponseEntity<List<PublicationResponse>> publications() {
        try {
            List<Publication> publicationList = publicationService.getPublications();
            if (publicationList.isEmpty()) {
                return ResponseEntity.ofNullable(Collections.singletonList(
                        new PublicationResponse("Success", publicationList)));
            }
            return ResponseEntity.ok(Collections.singletonList(
                    new PublicationResponse("Success", publicationList)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Collections.singletonList(
                    new PublicationResponse(
                            "No se encontraron publicaciones en la bd " + e.getMessage(),
                            null)));
        }

    }

    @GetMapping("/publications/{id}")
    public ResponseEntity<Optional<Publication>> getPublication(@PathVariable Long id) {
        return ResponseEntity.ok(publicationService.getPublication(id));
    }

    @GetMapping("/publications/average/{id}")
    public ResponseEntity<Map<String, String>> getPublicationAverage(@PathVariable Long id) {
        return ResponseEntity.ok(publicationService.getAverage(id));
    }
}
