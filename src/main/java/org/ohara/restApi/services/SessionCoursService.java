package org.ohara.restApi.services;

import org.ohara.maVraiDep.data.entitties.Etudiant;
import org.ohara.maVraiDep.data.entitties.SessionCours;
import org.ohara.maVraiDep.data.entitties.SessionCoursEtudiant;
import org.ohara.maVraiDep.data.enums.EEtatSession;
import org.ohara.maVraiDep.data.web.dto.request.CoursRequestDto;
import org.ohara.maVraiDep.data.web.dto.request.SessionCoursRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.YearMonth;

public interface SessionCoursService {
    Page<SessionCours> getSessionCours(String module, Long professeurId, Pageable page);
    Page<SessionCours> getSessionsByProfessorForCurrentMonth(Pageable pageable,Long professorId,String module);
    Page<SessionCours> getSessionsByProfessorOnly(Long professorId,Pageable page,String period);
    long getTotalHoursByProfessorForMonth(Long professorId, String module, YearMonth month);

    Page<SessionCours> getSessionCoursByEtatValid(Pageable page);
    Page<SessionCoursEtudiant> getEtudiantsBySessionCoursAndClasse(Long sessionId, String libelle, Pageable pageable);

    void addSessionCours(SessionCoursRequestDto dto, Long coursId);
    void cancelSessionCours(Long sessionId);

}
