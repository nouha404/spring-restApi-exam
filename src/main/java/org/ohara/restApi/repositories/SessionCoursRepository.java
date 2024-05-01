package org.ohara.restApi.repositories;

import org.ohara.maVraiDep.data.entitties.Etudiant;
import org.ohara.maVraiDep.data.entitties.SessionCours;
import org.ohara.maVraiDep.data.entitties.SessionCoursEtudiant;
import org.ohara.maVraiDep.data.enums.EEtatSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SessionCoursRepository extends JpaRepository<SessionCours, Long> {
    Page<SessionCours> findAllByIsActiveTrue(Pageable page);
    @Query("SELECT sc FROM SessionCours sc WHERE sc.cours.module.id = :moduleId")

    Page<SessionCours> findByModule(@Param("moduleId") String moduleId , Pageable page);
    @Query(
            "SELECT sc FROM SessionCours sc " +
                    "JOIN sc.cours.professeur p " +
                    "JOIN sc.cours.module m " +
                    "WHERE p.id= :professorId" +
                    " AND m.libelle = :module " +
                    "AND MONTH(sc.date) = MONTH(CURRENT_DATE) " +
                    "AND YEAR(sc.date) = YEAR(CURRENT_DATE) " +
                    "AND sc.isActive = true"
    )
    Page<SessionCours> findByProfessorAndModule(Pageable pageable, @Param("professorId") Long professorId, @Param("module") String module);



    @Query(
            "SELECT sc FROM SessionCours sc " +
                    "JOIN sc.cours.professeur p " +
                    "WHERE p.id= :professorId" +
                    " AND MONTH(sc.date) = MONTH(CURRENT_DATE) " +
                    "AND YEAR(sc.date) = YEAR(CURRENT_DATE) " +
                    "AND sc.isActive = true"
    )
    Page<SessionCours> findByProfessorForCurrentMonth(Pageable page, @Param("professorId") Long professorId);

    /*@Query("SELECT sce.etudiant FROM SessionCoursEtudiant sce WHERE sce.sessionCours.id = :sessionId")
    Page<Etudiant> findByEtudiant(@Param("sessionId") Long sessionId, Pageable page);*/

    @Query("SELECT sce FROM SessionCoursEtudiant sce WHERE sce.sessionCours.id = :sessionId")
    Page<SessionCoursEtudiant> findByEtudiant(@Param("sessionId") Long sessionId, Pageable page);


    @Query("SELECT sc FROM SessionCours sc " +
            "JOIN sc.cours.professeur p " +
            "WHERE p.id = :professorId " +
            "AND sc.date = :selectedDate " +
            "AND sc.isActive = true")
    Page<SessionCours> findByProfessorAndDate(@Param("professorId") Long professorId,
                                              @Param("selectedDate") LocalDate selectedDate,
                                              Pageable pageable);

    @Query("SELECT sc FROM SessionCours sc " +
            "WHERE sc.cours.professeur.id = :professorId " +
            "AND MONTH(sc.date) = MONTH(CURRENT_DATE) " +
            "AND YEAR(sc.date) = YEAR(CURRENT_DATE) " +
            "AND sc.isActive = true " +
            "AND sc.date BETWEEN :startDate AND :endDate")
    Page<SessionCours> findByProfessorForCurrentMonthAndDateBetween(
            Pageable pageable,
            @Param("professorId") Long professorId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    //nombre heure effectuer durant le mois
    /*@Query("SELECT COALESCE(SUM(sc.nombreHeurePlanifier), 0) FROM SessionCours sc " +
            "WHERE sc.cours.professeur.id = :professorId " +
            "AND MONTH(sc.date) = :monthValue " +
            "AND YEAR(sc.date) = :yearValue")
    long getTotalHoursByProfessorForMonth(@Param("professorId") Long professorId,
                                          @Param("monthValue") int monthValue,
                                          @Param("yearValue") int yearValue);

    @Query("SELECT COALESCE(SUM(sc.nombreHeurePlanifier), 0) FROM SessionCours sc " +
            "WHERE sc.cours.professeur.id = :professorId " +
            "AND sc.cours.module.id = :moduleId " +
            "AND MONTH(sc.date) = :monthValue " +
            "AND YEAR(sc.date) = :yearValue")
    long getTotalHoursByProfessorAndModuleForMonth(@Param("professorId") Long professorId,
                                                   @Param("moduleId") String moduleId,
                                                   @Param("monthValue") int monthValue,
                                                   @Param("yearValue") int yearValue);*/
    @Query("SELECT sc FROM SessionCours sc " +
            "WHERE sc.cours.professeur.id = :professorId " +
            "AND MONTH(sc.date) = :month " +
            "AND YEAR(sc.date) = :year")
    List<SessionCours> findByProfessorIdAndMonth(@Param("professorId") Long professorId,
                                                 @Param("month") int month,
                                                 @Param("year") int year);


    @Query("SELECT sc FROM SessionCours sc WHERE sc.etatSession = :etatSession")
    Page<SessionCours> findByEtatSessionValider(Pageable pageable, @Param("etatSession") EEtatSession etatSession);

    @Query("SELECT sce FROM SessionCoursEtudiant sce " +
            "WHERE sce.sessionCours.id = :sessionId " +
            "AND (:libelle IS NULL OR sce.classe.libelle = :libelle)")
    Page<SessionCoursEtudiant> findBySessionCoursIdAndClasseLibelle(@Param("sessionId") Long sessionId,
                                                                    @Param("libelle") String libelle,
                                                                    Pageable pageable);

}
