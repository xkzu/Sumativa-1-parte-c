package cl.duoc.sumativa1.app.partec.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class Publishing {

    private Long id;

    //persona que realiza la publicación
    private User user;

    private String title;

    private String content;

    //lista con los comentarios
    private List<Comment> comments;

    //lista con las calificaciones
    private List<Qualification> qualifications;
}
