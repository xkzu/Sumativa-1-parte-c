package cl.duoc.sumativa1.app.partec.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@AllArgsConstructor
@Data
public class QualificationResponse {

    private String message;

    private Optional<Qualification> qualification;
}
