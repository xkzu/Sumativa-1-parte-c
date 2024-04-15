package cl.duoc.sumativa1.app.partec.service;

import cl.duoc.sumativa1.app.partec.model.Qualification;
import cl.duoc.sumativa1.app.partec.repository.QualificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QualificationServiceImpl implements QualificationService {

    private final QualificationRepository qualificationRepository;

    public QualificationServiceImpl(QualificationRepository qualificationRepository) {
        this.qualificationRepository = qualificationRepository;
    }

    @Override
    public Map<String, Double> getAverage(Long id) {
        List<Qualification> qualifications = qualificationRepository.findByIdPublication(id);
        double sum = 0.0;
        if (qualifications.isEmpty()) {
            return Map.of("No se encontraron calificaciones para este id", sum);
        }
        for (Qualification qualification: qualifications) {
            sum += qualification.getQualifications();
        }
        return Map.of("Promedio", sum / qualifications.size());
    }
}
