package org.ohara.restApi.services;

import org.ohara.maVraiDep.data.entitties.Cours;
import org.ohara.maVraiDep.data.web.dto.request.CoursRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CoursService {
    Page<Cours> getCoursByProfesseur(Long id, Pageable page);
    Page<Cours> getCours(String etatCours, Pageable page);
    List<Cours> getCours();

    void addCours(CoursRequestDto dto);
}
