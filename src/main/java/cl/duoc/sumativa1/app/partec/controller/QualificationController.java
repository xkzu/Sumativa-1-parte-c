package cl.duoc.sumativa1.app.partec.controller;

import cl.duoc.sumativa1.app.partec.model.Qualification;
import cl.duoc.sumativa1.app.partec.model.QualificationResponse;
import cl.duoc.sumativa1.app.partec.model.QualificationsResponse;
import cl.duoc.sumativa1.app.partec.service.PublicationService;
import cl.duoc.sumativa1.app.partec.service.QualificationService;
import cl.duoc.sumativa1.app.partec.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/qualifications")
public class QualificationController {

    private final QualificationService qualificationService;

    private final PublicationService publicationService;

    @Autowired
    public QualificationController(QualificationService qualificationService, PublicationService publicationService) {
        this.qualificationService = qualificationService;
        this.publicationService = publicationService;
    }

    @GetMapping("/publications/average/{id}")
    public ResponseEntity<Map<String, Double>> getPublicationAverage(@PathVariable Long id) {
        try {
            //las validacion la hice en la implementacion
            return ResponseEntity.ok(qualificationService.getAverage(id));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "Error al obtener promedio de calificaciones por id " + e.getMessage(), 0.0));
        }

    }

    @PostMapping("/add")
    public ResponseEntity<QualificationResponse> addQualification(@RequestBody Qualification qualification) {
        try {
            if (qualification.getQualifications() < 1 || qualification.getIdPublication() < 1) {
                return ResponseEntity.badRequest().body(
                        new QualificationResponse("idPublication o qualifications no pueden ser cero",null));
            }
            if (publicationService.getPublication(qualification.getIdPublication()).isEmpty()) {
                return ResponseEntity.ofNullable(
                        new QualificationResponse("No se encuentra idPublicacion en bd",null));
            }
            return ResponseEntity.ok(
                    new QualificationResponse(Constant.SUCCESS, Optional.of(qualificationService.add(qualification))));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new QualificationResponse("Error al ingresar una calificación " +e.getMessage(), null));
        }
    }

    @GetMapping("/publication/{id}")
    public ResponseEntity<QualificationsResponse> getQualificationsByIdPublication(@PathVariable Long id) {
        try {
            if ( id < 1) {
                return ResponseEntity.badRequest().body(
                        new QualificationsResponse("id no puede ser cero", null));
            }
            if (qualificationService.getQualificationsByIdPublication(id).isEmpty()) {
                return ResponseEntity.ofNullable(
                        new QualificationsResponse("No se encuentra idPublicacion en bd", null));
            }
            return ResponseEntity.ok(
                    new QualificationsResponse(Constant.SUCCESS, qualificationService.getQualificationsByIdPublication(id)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new QualificationsResponse("Error al listar las calidicaciones " + e.getMessage(), null));
        }
    }

    @GetMapping("")
    public ResponseEntity<QualificationsResponse> qualifications() {
        try {
            if (qualificationService.getQualifications().isEmpty()) {
                return ResponseEntity.ofNullable(
                        new QualificationsResponse("No se encuentran calificaciones en la bd", null));
            }
            return ResponseEntity.ok(
                    new QualificationsResponse(Constant.SUCCESS, qualificationService.getQualifications()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new QualificationsResponse("Error al buscar calificaiones en bd " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<QualificationResponse> deleteQualification(@PathVariable Long id) {
        try {
            if (id < 1) {
                return ResponseEntity.badRequest().body(
                        new QualificationResponse("id no puede ser cero", null));
            }
            Optional<Qualification> qualification = qualificationService.getQulification(id);
            if (qualification.isEmpty()) {
                return ResponseEntity.ofNullable(
                        new QualificationResponse("No se encuentra este id en la bd", null));
            }
            qualificationService.deleteQualification(id);
            return ResponseEntity.ok(new QualificationResponse(Constant.SUCCESS, qualification));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new QualificationResponse("Error al eliminar qualification " + e.getMessage(), null));
        }
    }
}
