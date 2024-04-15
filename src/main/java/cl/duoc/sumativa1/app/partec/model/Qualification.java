package cl.duoc.sumativa1.app.partec.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "qualifications")
@AllArgsConstructor //le indicamos a loombok que nos genere el constructor con parametros
@NoArgsConstructor
@Data //le decimos a loombok que se encargue de generar los get y set
public class Qualification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qualification_id")
    private Long idQualification;

    @Column(name = "publication_id")
    private Long idPublication;

    @Column(name = "qualification")
    private double qualifications;
}
