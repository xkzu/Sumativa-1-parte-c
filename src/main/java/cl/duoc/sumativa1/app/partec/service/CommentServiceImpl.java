package cl.duoc.sumativa1.app.partec.service;

import cl.duoc.sumativa1.app.partec.model.Comment;
import cl.duoc.sumativa1.app.partec.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> getCommnetsById(Long id) {
        return commentRepository.findByIdPublication(id);
    }
}
