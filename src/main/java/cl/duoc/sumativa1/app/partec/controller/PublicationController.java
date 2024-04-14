package cl.duoc.sumativa1.app.partec.controller;

import cl.duoc.sumativa1.app.partec.model.Publication;
import cl.duoc.sumativa1.app.partec.model.PublicationResponse;
import cl.duoc.sumativa1.app.partec.model.PublicationsResponse;
import cl.duoc.sumativa1.app.partec.service.PublicationService;
import cl.duoc.sumativa1.app.partec.util.Constant;
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
    public ResponseEntity<List<PublicationsResponse>> publications() {
        try {
            List<Publication> publicationList = publicationService.getPublications();
            if (publicationList.isEmpty()) {
                return ResponseEntity.ofNullable(Collections.singletonList(
                        new PublicationsResponse(Constant.SUCCESS, publicationList)));
            }
            return ResponseEntity.ok(Collections.singletonList(
                    new PublicationsResponse(Constant.SUCCESS, publicationList)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Collections.singletonList(
                    new PublicationsResponse(
                            "Error al obtener publicaciones de la bd " + e.getMessage(),
                            null)));
        }

    }

    @GetMapping("/publications/{id}")
    public ResponseEntity<Optional<PublicationResponse>> getPublication(@PathVariable Long id) {
        try {
            if (null == id) {
                return ResponseEntity.badRequest().body(Optional.of(
                        new PublicationResponse("id no puede ser null", null)));
            }
            Optional<Publication> publication = publicationService.getPublication(id);
            if (publication.isEmpty()) {
                return ResponseEntity.ofNullable(Optional.of(new PublicationResponse(
                        "No se encontró publicación", publication)));
            }
            return ResponseEntity.ok(Optional.of(new PublicationResponse(Constant.SUCCESS, publication)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Optional.of(
                    new PublicationResponse("Error al obtener publicación " + e.getMessage(), null)));
        }
    }

    @GetMapping("/publications/average/{id}")
    public ResponseEntity<Map<String, String>> getPublicationAverage(@PathVariable Long id) {
        return ResponseEntity.ok(publicationService.getAverage(id));
    }
}
