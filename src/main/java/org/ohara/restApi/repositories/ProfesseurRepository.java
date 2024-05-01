package org.ohara.restApi.repositories;

import org.ohara.maVraiDep.data.entitties.Professeur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfesseurRepository extends JpaRepository<Professeur,Long> {
    Page<Professeur> findAllByIsActiveTrue(Pageable pageable);
    Optional<Professeur>  findByNomAndPrenom(String nom, String prenom);
}
