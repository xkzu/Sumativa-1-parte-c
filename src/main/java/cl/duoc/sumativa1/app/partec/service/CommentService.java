package cl.duoc.sumativa1.app.partec.service;

import cl.duoc.sumativa1.app.partec.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    List<Comment> getCommnetsById(Long id);

    Comment addComment(Comment comment);

    Optional<Comment> getCommentByid(Long id);

    void deleteComment(Long id);
}
