package cl.duoc.sumativa1.app.partec.service;

import cl.duoc.sumativa1.app.partec.model.Publication;
import cl.duoc.sumativa1.app.partec.repository.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PublicationServiceImpl implements PublicationService {

    final PublicationRepository repository;

    @Autowired
    public PublicationServiceImpl(PublicationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Publication> getPublications() {
        return repository.findAll();
    }

    public Optional<Publication> getPublication(Long id) {
        return repository.findById(id);
    }

    @Override
    public Map<String, String> getAverage(Long id) {
        Map<String, String> averageResponse = new HashMap<>();

        Optional<Publication> publication = getPublication(id);

//        averageResponse.put("id", String.valueOf(publication.getId()));
//        averageResponse.put("user", publication.getUser());
//        averageResponse.put("title", publication.getTitle());
//        averageResponse.put("content", publication.getContent());
//        averageResponse.put("comment", publication.getComment().toString());
//        averageResponse.put("qualifications", publication.getQualifications().toString());
//        averageResponse.put("average", String.valueOf(getAverageCalculate(publication.getQualifications())));
        return averageResponse;
    }

    private Double getAverageCalculate(List<Double> qualifications) {
        double sum = 0.0;
        for (Double value: qualifications) {
            sum += value;
        }
        return sum / qualifications.size();
    }

}
