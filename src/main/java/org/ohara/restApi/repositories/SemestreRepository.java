package org.ohara.restApi.repositories;

import org.ohara.maVraiDep.data.entitties.Semestre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SemestreRepository extends JpaRepository<Semestre,Long> {
    Optional<Semestre> findByLibelle(String libelle);
}
