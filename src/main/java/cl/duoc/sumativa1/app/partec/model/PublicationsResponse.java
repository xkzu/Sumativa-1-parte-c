package cl.duoc.sumativa1.app.partec.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class PublicationsResponse {

    private String message;

    private List<Publication> publication;
}
