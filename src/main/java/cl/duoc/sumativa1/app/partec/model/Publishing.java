package cl.duoc.sumativa1.app.partec.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class Publishing {

    private int id;

    private String user;

    private String title;

    private String content;

    private List<String> comment;

    //lista con las calificaciones
    private List<Double> qualifications;
}
