package org.ohara.restApi.services;

import org.ohara.maVraiDep.data.entitties.AnneeScolaire;
import org.ohara.maVraiDep.data.web.dto.request.AnneeScolaireRequestDto;

public interface AnneeScolaireService {
    void createAnneeScolaire(AnneeScolaireRequestDto anneeScolaireRequestDto);

    AnneeScolaire getAnneScolaireByLibelle(String libelle);

    void updateAnneScolaireActived(String libelle);
}
