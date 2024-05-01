package org.ohara.restApi.services.impl;

import lombok.RequiredArgsConstructor;
import org.ohara.maVraiDep.data.entitties.Cours;
import org.ohara.maVraiDep.data.entitties.Professeur;
import org.ohara.restApi.repositories.CoursRepository;
import org.ohara.restApi.repositories.ProfesseurRepository;
import org.ohara.restApi.services.ProfesseurService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfesseurServiceImpl implements ProfesseurService {
    private final ProfesseurRepository professeurRepository;
    @Override
    public Page<Professeur> getProfesseur(Pageable page) {
        return professeurRepository.findAllByIsActiveTrue(page);
    }
}
