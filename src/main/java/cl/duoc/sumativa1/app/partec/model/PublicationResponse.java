package cl.duoc.sumativa1.app.partec.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@AllArgsConstructor
@Data
public class PublicationResponse {

    private String message;

    private Optional<Publication> publication;
}
