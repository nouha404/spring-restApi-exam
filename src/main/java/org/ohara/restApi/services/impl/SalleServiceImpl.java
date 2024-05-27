package org.ohara.restApi.services.impl;


import lombok.RequiredArgsConstructor;
import org.ohara.maVraiDep.data.entitties.Salle;
import org.ohara.maVraiDep.data.web.dto.request.SalleRequestDto;
import org.ohara.restApi.repositories.SalleRepository;
import org.ohara.restApi.services.SalleService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class SalleServiceImpl implements SalleService {
    private final SalleRepository salleRepository;
    @Override
    public void addSalle(SalleRequestDto dto) {
        Salle salle = dto.TransformToEntity();
        salle.setIsActive(true);
        salle.setNom(salle.getNom());
        salle.setNom(salle.getNom());
        salle.setNbrPlace(salle.getNbrPlace());
        salleRepository.save(salle);
    }

    @Override
    public List<Salle> getAllSalles() {
        return salleRepository.findAllByIsActiveTrue();
    }
}
