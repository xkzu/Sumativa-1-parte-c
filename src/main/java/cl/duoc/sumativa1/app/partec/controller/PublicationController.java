package cl.duoc.sumativa1.app.partec.controller;

import cl.duoc.sumativa1.app.partec.model.Publication;
import cl.duoc.sumativa1.app.partec.service.PublicationService;
import cl.duoc.sumativa1.app.partec.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public ResponseEntity<CollectionModel<EntityModel<Publication>>> publications() {
        try {
            List<Publication> publicationList = publicationService.getPublications();
            if (publicationList.isEmpty()) {
                return ResponseEntity.ofNullable(CollectionModel.empty());
            }

            List<EntityModel<Publication>> publicationModel = publicationList.stream()
                    .map(publication -> EntityModel.of(publication,
                            linkTo(methodOn(this.getClass()).getPublication(publication.getId())).withSelfRel()))
                    .collect(Collectors.toList());

            CollectionModel<EntityModel<Publication>> collectionModel = CollectionModel.of(publicationModel,
                    linkTo(methodOn(this.getClass()).publications()).withSelfRel());

            return ResponseEntity.ok(collectionModel);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }

//    @GetMapping("/publications")
//    public ResponseEntity<List<PublicationsResponse>> publications() {
//        try {
//            List<Publication> publicationList = publicationService.getPublications();
//            if (publicationList.isEmpty()) {
//                return ResponseEntity.ofNullable(Collections.singletonList(
//                        new PublicationsResponse(Constant.SUCCESS, publicationList)));
//            }
//            return ResponseEntity.ok(Collections.singletonList(
//                    new PublicationsResponse(Constant.SUCCESS, publicationList)));
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().body(Collections.singletonList(
//                    new PublicationsResponse(
//                            "Error al obtener publicaciones de la bd " + e.getMessage(),
//                            null)));
//        }
//
//    }

    @GetMapping("/publications/{id}")
    public ResponseEntity<EntityModel<EntityModel<Optional<Publication>>>> getPublication(@PathVariable Long id) {
        try {
            if (null == id) {
                return ResponseEntity.badRequest().body(EntityModel.of(null));
            }

            Optional<Publication> publication = publicationService.getPublication(id);
            EntityModel<Optional<Publication>> model = EntityModel.of(publication,
                    linkTo(methodOn(this.getClass()).getPublication(id)).withSelfRel());

            if (publication.isEmpty()) {
                return ResponseEntity.ok(EntityModel.of(model));
            }

            return ResponseEntity.ok(EntityModel.of(model));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

//    @GetMapping("/publications/{id}")
//    public ResponseEntity<Optional<PublicationResponse>> getPublication(@PathVariable Long id) {
//        try {
//            if (null == id) {
//                return ResponseEntity.badRequest().body(Optional.of(
//                        new PublicationResponse("id no puede ser null", null)));
//            }
//            Optional<Publication> publication = publicationService.getPublication(id);
//            if (publication.isEmpty()) {
//                return ResponseEntity.ofNullable(Optional.of(new PublicationResponse(
//                        "No se encontró publicación", publication)));
//            }
//            return ResponseEntity.ok(Optional.of(new PublicationResponse(Constant.SUCCESS, publication)));
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().body(Optional.of(
//                    new PublicationResponse("Error al obtener publicación " + e.getMessage(), null)));
//        }
//    }

    @PostMapping("/publications/add")
    public ResponseEntity<EntityModel<Publication>> addPublication(@RequestBody Publication publication) {
        try {
            if (ValidateUtil.isEmptyOrNull(publication.getTitle())
                    || ValidateUtil.isEmptyOrNull(publication.getUser())
                    || ValidateUtil.isEmptyOrNull(publication.getContent())) {
                return ResponseEntity.badRequest().build();
            }
            Publication savedPublication = publicationService.addPublication(publication);
            EntityModel<Publication> responseModel = EntityModel.of(publication,
                    linkTo(methodOn(this.getClass()).addPublication(publication)).withSelfRel(),
                    linkTo(methodOn(this.getClass()).getPublication(savedPublication.getId())).withRel("view-publication"),
                    linkTo(methodOn(this.getClass()).deletePublication(savedPublication.getId())).withRel("delete-publication"));
            return ResponseEntity.ok(responseModel);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }

//    @PostMapping("/publications/add")
//    public ResponseEntity<PublicationResponse> addPublication(@RequestBody Publication publication) {
//        try {
//            if (ValidateUtil.isEmptyOrNull(publication.getTitle())
//                    || ValidateUtil.isEmptyOrNull(publication.getUser())
//                    || ValidateUtil.isEmptyOrNull(publication.getContent())) {
//                return ResponseEntity.badRequest().body(new PublicationResponse(
//                        "Title, user, content no pueden ser null o vacío", null));
//            }
//            return ResponseEntity.ok(new PublicationResponse(
//                    Constant.SUCCESS, Optional.of(publicationService.addPublication(publication))));
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().body(new PublicationResponse(
//                    "Error al ingresar la publicación " + e.getMessage(), null));
//        }
//
//    }

    @PutMapping("/publications/update/{id}")
    public ResponseEntity<EntityModel<Publication>> updatePublication(@PathVariable Long id, @RequestBody Publication publication) {
        try {
            if (id < 1
                    || ValidateUtil.isEmptyOrNull(publication.getTitle())
                    || ValidateUtil.isEmptyOrNull(publication.getUser())
                    || ValidateUtil.isEmptyOrNull(publication.getContent())) {
                return ResponseEntity.badRequest().build();
            }
            if (publicationService.getPublication(id).isEmpty()) {
                return ResponseEntity.ofNullable(EntityModel.of(publication));
            }
            publication.setId(id);
            Publication updatePublication = publicationService.updatePublication(publication);
            EntityModel<Publication> responseModel = EntityModel.of(publication,
                    linkTo(methodOn(this.getClass()).updatePublication(id, publication)).withSelfRel(),
                    linkTo(methodOn(this.getClass()).getPublication(updatePublication.getId())).withRel("view-publication"),
                    linkTo(methodOn(this.getClass()).deletePublication(updatePublication.getId())).withRel("delete-publication"));
            return ResponseEntity.ok(responseModel);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

//    @PutMapping("/publications/update/{id}")
//    public ResponseEntity<PublicationResponse> updatePublication(@PathVariable Long id, @RequestBody Publication publication) {
//        try {
//            if (id < 1
//                    || ValidateUtil.isEmptyOrNull(publication.getTitle())
//                    || ValidateUtil.isEmptyOrNull(publication.getUser())
//                    || ValidateUtil.isEmptyOrNull(publication.getContent())) {
//                return ResponseEntity.badRequest().body(new PublicationResponse(
//                        "id, title, user, content no pueden ser null o vacío", null));
//            }
//            if (publicationService.getPublication(id).isEmpty()) {
//                return ResponseEntity.ofNullable(
//                        new PublicationResponse("El id ingresado no existe en la bd", null));
//            }
//            publication.setId(id);
//            return ResponseEntity.ok(new PublicationResponse(
//                    Constant.SUCCESS, Optional.of(publicationService.updatePublication(publication))));
//
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().body(new PublicationResponse(
//                    "Error al actualizar " + e.getMessage(),null));
//        }
//    }

    @DeleteMapping("/publications/delete/{id}")
    public ResponseEntity<EntityModel<Optional<Publication>>> deletePublication(@PathVariable Long id) {
        try {
            if (id < 1) {
                return ResponseEntity.badRequest().build();
            }
            Optional<Publication> publication = publicationService.getPublication(id);
            if (publication.isEmpty()) {
                return ResponseEntity.ofNullable(EntityModel.of(publication));
            }
            publicationService.deletePublication(id);
            EntityModel<Optional<Publication>> responseModel = EntityModel.of(publication,
                    linkTo(methodOn(this.getClass()).deletePublication(id)).withRel("delete-publication"));
            return ResponseEntity.ok(responseModel);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

//    @DeleteMapping("/publications/delete/{id}")
//    public ResponseEntity<PublicationResponse> deletePublication(@PathVariable Long id) {
//        try {
//            if (id < 1) {
//                return ResponseEntity.badRequest().body(new PublicationResponse(
//                        "El id ingresado debe ser mayor a cero", null));
//            }
//            Optional<Publication> publication = publicationService.getPublication(id);
//            if (publication.isEmpty()) {
//                return ResponseEntity.ofNullable(
//                        new PublicationResponse("El id ingresado no existe en la bd", null));
//            }
//            publicationService.deletePublication(id);
//            return ResponseEntity.ok(
//                    new PublicationResponse(Constant.SUCCESS, publication));
//        } catch (DataIntegrityViolationException e) {
//            // debido a que no me queda tiempo, solo valide que no se pueda borar, con mas tiempo implementaria borrar en cascada
//            return ResponseEntity.internalServerError().body(
//                    new PublicationResponse(
//                            "No se puede borrar la publicacion debido a que tiene calificaiones o comentarios",
//                            null));
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().body(new PublicationResponse(
//                    "Error al eliminar publicación " + e.getMessage(),null));
//        }
//    }

}
