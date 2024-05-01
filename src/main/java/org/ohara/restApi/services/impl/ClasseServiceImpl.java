package org.ohara.restApi.services.impl;

import lombok.RequiredArgsConstructor;

import org.ohara.maVraiDep.data.entitties.Classe;
import org.ohara.maVraiDep.data.entitties.Cours;
import org.ohara.maVraiDep.data.web.dto.request.ClasseRequestDto;
import org.ohara.restApi.repositories.ClasseRepository;
import org.ohara.restApi.services.ClasseService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class ClasseServiceImpl implements ClasseService {
    private final ClasseRepository classeRepository;
    @Override
    public void addClasse(ClasseRequestDto dto) {
        Classe classe = dto.TransformToEntity();
        classe.setLibelle(classe.getLibelle().toUpperCase());
        classe.setIsActive(true);
        classeRepository.save(classe);

    }
}
