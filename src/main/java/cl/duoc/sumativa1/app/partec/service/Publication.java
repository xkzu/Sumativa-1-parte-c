package cl.duoc.sumativa1.app.partec.service;

import cl.duoc.sumativa1.app.partec.model.Publishing;

import java.util.List;

public interface Publication {

    List<Publishing> getPublications();

    Publishing getPublication(int id);
}
