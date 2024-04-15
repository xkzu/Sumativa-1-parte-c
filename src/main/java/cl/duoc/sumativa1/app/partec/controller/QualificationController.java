package cl.duoc.sumativa1.app.partec.controller;

import cl.duoc.sumativa1.app.partec.service.QualificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/qualifications")
public class QualificationController {

    private final QualificationService qualificationService;

    public QualificationController(QualificationService qualificationService) {
        this.qualificationService = qualificationService;
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
}
