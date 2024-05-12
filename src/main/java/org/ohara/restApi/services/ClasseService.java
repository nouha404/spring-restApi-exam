package org.ohara.restApi.services;

import org.ohara.maVraiDep.data.entitties.Classe;
import org.ohara.maVraiDep.data.entitties.Cours;
import org.ohara.maVraiDep.data.web.dto.request.ClasseRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ClasseService {
    void addClasse(ClasseRequestDto dto);
    Page<Classe>  getAllClasse(Pageable page);
}
