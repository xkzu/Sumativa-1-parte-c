package cl.duoc.sumativa1.app.partec.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Qualification {

    private int id;

    private int qualifications;

    private User user;

    private Comment comment;
}
