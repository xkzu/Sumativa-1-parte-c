package cl.duoc.sumativa1.app.partec.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CommentsResponse {

    private String message;

    private List<Comment> comments;
}
