package org.ohara.restApi.services;


import org.ohara.maVraiDep.data.entitties.Salle;
import org.ohara.maVraiDep.data.web.dto.request.SalleRequestDto;

import java.util.List;

public interface SalleService {
    void addSalle(SalleRequestDto dto);
    List<Salle> getAllSalles();
}
