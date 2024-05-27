package org.ohara.restApi.repositories;

import org.ohara.maVraiDep.data.entitties.Salle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalleRepository extends JpaRepository<Salle, Long> {
    List<Salle> findAllByIsActiveTrue();
}
