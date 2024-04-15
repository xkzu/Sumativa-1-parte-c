package cl.duoc.sumativa1.app.partec.controller;

import cl.duoc.sumativa1.app.partec.model.CommentsResponse;
import cl.duoc.sumativa1.app.partec.service.CommentService;
import cl.duoc.sumativa1.app.partec.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentsResponse> getCommentsById(@PathVariable Long id) {
        try {
            if (id < 1) {
                return ResponseEntity.badRequest().body(
                        new CommentsResponse("id no puede ser cero", null));
            }
            if (commentService.getCommnetsById(id).isEmpty()) {
                return ResponseEntity.ofNullable(
                        new CommentsResponse("No se encontraron comentarios",null));
            }
            return ResponseEntity.ok(new CommentsResponse(Constant.SUCCESS, commentService.getCommnetsById(id)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new CommentsResponse("Error al buscar comentarios " + e.getMessage(), null));
        }
    }
}
