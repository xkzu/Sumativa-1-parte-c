package cl.duoc.sumativa1.app.partec.controller;

import cl.duoc.sumativa1.app.partec.model.Comment;
import cl.duoc.sumativa1.app.partec.model.CommentResponse;
import cl.duoc.sumativa1.app.partec.model.CommentsResponse;
import cl.duoc.sumativa1.app.partec.service.CommentService;
import cl.duoc.sumativa1.app.partec.service.PublicationService;
import cl.duoc.sumativa1.app.partec.util.Constant;
import cl.duoc.sumativa1.app.partec.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    private final CommentService commentService;

    private final PublicationService publicationService;

    @Autowired
    public CommentController(CommentService commentService, PublicationService publicationService) {
        this.commentService = commentService;
        this.publicationService = publicationService;
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

    @PostMapping("comments/add")
    public ResponseEntity<CommentResponse> addComment(@RequestBody Comment comment) {
        try {
            if (comment.getIdPublication() < 1 || ValidateUtil.isEmptyOrNull(comment.getComments())) {
                return ResponseEntity.badRequest().body(
                        new CommentResponse(
                                "idPublicacion debe ser mayor a cero y comentarios no pueden ser vacio ni null",
                                null));
            }
            if (publicationService.getPublication(comment.getIdPublication()).isEmpty()) {
                return ResponseEntity.ofNullable(
                        new CommentResponse("idPublication no existe en bd", null));
            }
            return ResponseEntity.ok(new CommentResponse(Constant.SUCCESS, commentService.addComment(comment)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new CommentResponse("Error al agregar comentario " + e.getMessage(), null));
        }
    }
}
