package org.ohara.restApi.services.impl;

import lombok.RequiredArgsConstructor;
import org.ohara.maVraiDep.data.entitties.AnneeScolaire;

import org.ohara.maVraiDep.data.web.dto.request.AnneeScolaireRequestDto;
import org.ohara.restApi.repositories.AnneeScolaireRepository;
import org.ohara.restApi.services.AnneeScolaireService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnneeScolaireServiceImpl implements AnneeScolaireService {
    private final AnneeScolaireRepository anneeScolaireRepository;
    @Override
    public void createAnneeScolaire(AnneeScolaireRequestDto anneeScolaireRequestDto) {
        AnneeScolaire anneeScolaire = anneeScolaireRequestDto.TransformToEntity();
        anneeScolaire.setLibelle(anneeScolaire.getLibelle());
        anneeScolaire.setIsActive(false);
        anneeScolaire.setFinDePeriod(anneeScolaire.getFinDePeriod());
        anneeScolaireRepository.save(anneeScolaire);
    }

    @Override
    public AnneeScolaire getAnneScolaireByLibelle(String libelle) {
        return anneeScolaireRepository.findByLibelle(libelle);
    }

    @Override
    public void updateAnneScolaireActived(String libelle) {
        AnneeScolaire anneeScolaire = anneeScolaireRepository.findByLibelle(libelle);
        anneeScolaire.setIsActive(true);
        anneeScolaireRepository.save(anneeScolaire);

        //les autres anneeScolaire
        List<AnneeScolaire> autresAnneesScolaires = anneeScolaireRepository.findAll();
        autresAnneesScolaires.forEach(autreAnneeScolaire -> {
            if (!autreAnneeScolaire.getLibelle().equals(libelle)) {
                autreAnneeScolaire.setIsActive(false);
                anneeScolaireRepository.save(autreAnneeScolaire);
            }
        });
    }
}
