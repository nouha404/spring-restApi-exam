package org.ohara.restApi.services.impl;

import lombok.RequiredArgsConstructor;

import org.ohara.maVraiDep.data.entitties.Semestre;
import org.ohara.maVraiDep.data.web.dto.request.SemestreRequestDto;
import org.ohara.restApi.repositories.SemestreRepository;
import org.ohara.restApi.services.SemestreService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SemestreServiceImpl implements SemestreService {
    private final SemestreRepository semestreRepository;
    @Override
    public void addSemestre(SemestreRequestDto dto) {
        Semestre semestre = dto.TransformToEntity();
        semestre.setLibelle(semestre.getLibelle());
        semestre.setIsActive(true);
        semestreRepository.save(semestre);
    }


    @Override
    public List<Semestre> getAllSemestre() {
        return semestreRepository.findAllByIsActiveTrue();
    }
}
