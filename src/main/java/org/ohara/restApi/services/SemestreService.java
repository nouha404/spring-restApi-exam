package org.ohara.restApi.services;

import org.ohara.maVraiDep.data.entitties.Semestre;
import org.ohara.maVraiDep.data.web.dto.request.SemestreRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SemestreService {
    void addSemestre(SemestreRequestDto dto);
    List<Semestre> getAllSemestre();
}
