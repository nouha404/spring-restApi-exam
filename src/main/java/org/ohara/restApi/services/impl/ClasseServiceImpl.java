package org.ohara.restApi.services.impl;

import lombok.RequiredArgsConstructor;

import org.ohara.maVraiDep.data.entitties.Classe;
import org.ohara.maVraiDep.data.entitties.Cours;
import org.ohara.maVraiDep.data.entitties.Filiere;
import org.ohara.maVraiDep.data.entitties.Niveau;
import org.ohara.maVraiDep.data.web.dto.request.ClasseRequestDto;
import org.ohara.restApi.repositories.ClasseRepository;
import org.ohara.restApi.services.ClasseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class ClasseServiceImpl implements ClasseService {
    private final ClasseRepository classeRepository;
    @Override
    public void addClasse(ClasseRequestDto dto) {
        
        try {
            // Création de la classe à partir du DTO
            Classe classe = dto.TransformToEntity();
            classe.setLibelle(classe.getLibelle().toUpperCase());
            classe.setIsActive(true);

            // Sauvegarde de la classe
            classeRepository.save(classe);
        } catch (Exception e) {
            // Gestion des exceptions
            throw new RuntimeException("Erreur lors de l'ajout de la classe: " + e.getMessage(), e);
        }

    }

    @Override
    public Page<Classe> getAllClasse(Pageable page) {
        return classeRepository.findAll(page);
    }
}
