package cl.duoc.sumativa1.app.partec.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comments")
@AllArgsConstructor //le indicamos a loombok que nos genere el constructor con parametros
@NoArgsConstructor
@Data //le decimos a loombok que se encargue de generar los get y set
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private long id;

    @Column(name = "publication_id")
    private long idPublication;

    @Column(name = "comments")
    private String comments;

}
