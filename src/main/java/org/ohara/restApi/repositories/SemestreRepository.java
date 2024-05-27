package org.ohara.restApi.repositories;

import org.ohara.maVraiDep.data.entitties.Professeur;
import org.ohara.maVraiDep.data.entitties.Semestre;
import org.ohara.maVraiDep.data.web.dto.request.SemestreRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SemestreRepository extends JpaRepository<Semestre,Long> {
    Optional<Semestre> findByLibelle(String libelle);
    List<Semestre> findAllByIsActiveTrue();
    //Page<SemestreRequestDto> findAllByIsActiveTrue();
}
