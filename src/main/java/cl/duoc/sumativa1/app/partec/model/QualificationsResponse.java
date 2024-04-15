package cl.duoc.sumativa1.app.partec.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class QualificationsResponse {

    private String message;

    private List<Qualification> qualification;
}
