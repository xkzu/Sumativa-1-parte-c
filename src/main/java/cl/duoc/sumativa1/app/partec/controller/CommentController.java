package cl.duoc.sumativa1.app.partec.controller;

import cl.duoc.sumativa1.app.partec.model.Comment;
import cl.duoc.sumativa1.app.partec.model.CommentResponse;
import cl.duoc.sumativa1.app.partec.service.CommentService;
import cl.duoc.sumativa1.app.partec.service.PublicationService;
import cl.duoc.sumativa1.app.partec.util.Constant;
import cl.duoc.sumativa1.app.partec.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CommentController {

    private final CommentService commentService;

    private final PublicationService publicationService;

    @Autowired
    public CommentController(CommentService commentService, PublicationService publicationService) {
        this.commentService = commentService;
        this.publicationService = publicationService;
    }

/*
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
*/

    @GetMapping("/comments/{id}")
    public ResponseEntity<CollectionModel<EntityModel<Comment>>> getCommentsById(@PathVariable Long id) {
        if (id < 1) {
            return ResponseEntity.badRequest().build();
        }
        List<Comment> comments = commentService.getCommnetsById(id);
        if (comments.isEmpty()) {
            return ResponseEntity.ok(CollectionModel.empty());
        }

        List<EntityModel<Comment>> models = comments.stream()
                .map(comment -> EntityModel.of(comment,
                        linkTo(methodOn(CommentController.class).addComment(comment)).withRel("add-comment"),
                        linkTo(methodOn(CommentController.class).deleteComment(comment.getId())).withRel("delete-comment")))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Comment>> collectionModel = CollectionModel.of(models,
                linkTo(methodOn(CommentController.class).getCommentsById(id)).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

/*
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
            return ResponseEntity.ok(
                    new CommentResponse(Constant.SUCCESS, Optional.ofNullable(commentService.addComment(comment))));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new CommentResponse("Error al agregar comentario " + e.getMessage(), null));
        }
    }
*/

    @PostMapping("comments/add")
    public ResponseEntity<EntityModel<CommentResponse>> addComment(@RequestBody Comment comment) {
        try {
            if (comment.getIdPublication() < 1 || ValidateUtil.isEmptyOrNull(comment.getComments())) {
                return ResponseEntity.badRequest().body(
                        EntityModel.of(new CommentResponse(
                                "idPublicacion debe ser mayor a cero y comentarios no pueden ser vacio ni null",
                                Optional.empty())));
            }
            if (publicationService.getPublication(comment.getIdPublication()).isEmpty()) {
                return ResponseEntity.ok(
                        EntityModel.of(new CommentResponse("idPublication no existe en bd", Optional.empty())));
            }
            Comment savedComment = commentService.addComment(comment);
            EntityModel<CommentResponse> responseModel = EntityModel.of(new CommentResponse(Constant.SUCCESS, Optional.of(savedComment)),
                    linkTo(methodOn(CommentController.class).addComment(comment)).withSelfRel(),
                    linkTo(methodOn(CommentController.class).getCommentsById(savedComment.getId())).withRel("view-comment"),
                    linkTo(methodOn(CommentController.class).deleteComment(savedComment.getId())).withRel("delete-comment"));

            return ResponseEntity.ok(responseModel);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    EntityModel.of(new CommentResponse("Error al agregar comentario " + e.getMessage(), Optional.empty())));
        }
    }

/*
    @DeleteMapping("comments/delete/{id}")
    public ResponseEntity<CommentResponse> deleteComment(@PathVariable Long id) {
        try {
            if (id < 1) {
                return ResponseEntity.badRequest().body(
                        new CommentResponse("id no puede ser menor a 1", null));
            }
            if (commentService.getCommentByid(id).isEmpty()) {
                return ResponseEntity.ofNullable(
                        new CommentResponse("No se encontra id en la bd", null));
            }
            commentService.deleteComment(id);
            return ResponseEntity.ok(new CommentResponse(Constant.SUCCESS, commentService.getCommentByid(id)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new CommentResponse("Error al eliminar " + e.getMessage(), null));
        }
    }
*/

    @DeleteMapping("comments/delete/{id}")
    public ResponseEntity<EntityModel<CommentResponse>> deleteComment(@PathVariable Long id) {
        try {
            if (id < 1) {
                return ResponseEntity.badRequest().body(
                        EntityModel.of(new CommentResponse("id no puede ser menor a 1", null)));
            }
            Optional<Comment> commentOpt = commentService.getCommentByid(id);
            if (commentOpt.isEmpty()) {
                return ResponseEntity.ok(
                        EntityModel.of(new CommentResponse("No se encontró id en la bd", null),
                                linkTo(methodOn(CommentController.class).getCommentsById(id)).withRel("all-comments-id")));
            }
            commentService.deleteComment(id);
            return ResponseEntity.ok(
                    EntityModel.of(new CommentResponse(Constant.SUCCESS, null),
                            linkTo(methodOn(CommentController.class).getCommentsById(id)).withRel("all-comments-id"),
                            linkTo(methodOn(CommentController.class).addComment(new Comment())).withRel("add-comment")));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    EntityModel.of(new CommentResponse("Error al eliminar " + e.getMessage(), null)));
        }
    }

}
