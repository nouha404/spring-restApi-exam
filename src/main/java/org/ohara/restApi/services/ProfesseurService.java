package org.ohara.restApi.services;

import org.ohara.maVraiDep.data.entitties.Cours;
import org.ohara.maVraiDep.data.entitties.Professeur;
import org.ohara.maVraiDep.data.web.dto.request.ProfesseurSimpleResquestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProfesseurService {
    Page<Professeur> getProfesseur(Pageable page);
    void addProfesseur(ProfesseurSimpleResquestDto dto);
    Page<Professeur> getAllProfesseurs(Pageable page);
}
