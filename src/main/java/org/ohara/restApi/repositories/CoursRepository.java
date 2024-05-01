package org.ohara.restApi.repositories;

import org.ohara.maVraiDep.data.entitties.Cours;
import org.ohara.maVraiDep.data.entitties.Professeur;
import org.ohara.maVraiDep.data.enums.EtatCours;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CoursRepository extends JpaRepository<Cours,Long> {
    Page<Cours> findByProfesseur(Professeur professeur, Pageable pageable);
    Page<Cours> findAllByEtatCoursAndIsActiveTrue(EtatCours etatCours, Pageable pageable);
    Page<Cours> findAllByIsActiveTrue(Pageable page);
    List<Cours> findAllByIsActiveTrue();
    Cours findByIdAndIsActiveTrue(Long id);
}
