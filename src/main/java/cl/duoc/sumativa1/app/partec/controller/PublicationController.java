package cl.duoc.sumativa1.app.partec.controller;

import cl.duoc.sumativa1.app.partec.model.Publication;
import cl.duoc.sumativa1.app.partec.model.PublicationResponse;
import cl.duoc.sumativa1.app.partec.model.PublicationsResponse;
import cl.duoc.sumativa1.app.partec.service.PublicationService;
import cl.duoc.sumativa1.app.partec.util.Constant;
import cl.duoc.sumativa1.app.partec.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
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

    @PostMapping("/publications/add")
    public ResponseEntity<PublicationResponse> addPublication(@RequestBody Publication publication) {
        try {
            if (ValidateUtil.isEmptyOrNull(publication.getTitle())
                    || ValidateUtil.isEmptyOrNull(publication.getUser())
                    || ValidateUtil.isEmptyOrNull(publication.getContent())) {
                return ResponseEntity.badRequest().body(new PublicationResponse(
                        "Title, user, content no pueden ser null o vacío", null));
            }
            return ResponseEntity.ok(new PublicationResponse(
                    Constant.SUCCESS, Optional.of(publicationService.addPublication(publication))));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new PublicationResponse(
                    "Error al ingresar la publicación " + e.getMessage(), null));
        }

    }

    @PutMapping("/publications/update/{id}")
    public ResponseEntity<PublicationResponse> updatePublication(@PathVariable Long id, @RequestBody Publication publication) {
        try {
            if (id < 1
                    || ValidateUtil.isEmptyOrNull(publication.getTitle())
                    || ValidateUtil.isEmptyOrNull(publication.getUser())
                    || ValidateUtil.isEmptyOrNull(publication.getContent())) {
                return ResponseEntity.badRequest().body(new PublicationResponse(
                        "id, title, user, content no pueden ser null o vacío", null));
            }
            if (publicationService.getPublication(id).isEmpty()) {
                return ResponseEntity.ofNullable(
                        new PublicationResponse("El id ingresado no existe en la bd", null));
            }
            publication.setId(id);
            return ResponseEntity.ok(new PublicationResponse(
                    Constant.SUCCESS, Optional.of(publicationService.updatePublication(publication))));

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new PublicationResponse(
                    "Error al actualizar " + e.getMessage(),
                    Optional.of(publicationService.updatePublication(publication))));
        }
    }

}
