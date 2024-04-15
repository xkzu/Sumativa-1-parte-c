package cl.duoc.sumativa1.app.partec.service;

import cl.duoc.sumativa1.app.partec.model.Qualification;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface QualificationService {

    Map<String, Double> getAverage(Long id);

    Qualification add(Qualification qualification);

    List<Qualification> getQualificationsByIdPublication(Long id);

    List<Qualification> getQualifications();

    Optional<Qualification> getQulification(Long id);

    void deleteQualification(Long id);
}
