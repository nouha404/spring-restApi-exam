package org.ohara.restApi.services;

import org.ohara.maVraiDep.data.entitties.Cours;
import org.ohara.maVraiDep.data.entitties.Professeur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProfesseurService {
    Page<Professeur> getProfesseur(Pageable page);
}
