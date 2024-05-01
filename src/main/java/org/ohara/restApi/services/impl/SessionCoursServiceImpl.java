package org.ohara.restApi.services.impl;

import lombok.RequiredArgsConstructor;
import org.ohara.maVraiDep.data.entitties.Cours;
import org.ohara.maVraiDep.data.entitties.Etudiant;
import org.ohara.maVraiDep.data.entitties.SessionCours;

import org.ohara.maVraiDep.data.entitties.SessionCoursEtudiant;
import org.ohara.maVraiDep.data.enums.EEtatSession;
import org.ohara.maVraiDep.data.enums.ETypeSession;
import org.ohara.maVraiDep.data.web.dto.request.SessionCoursRequestDto;
import org.ohara.restApi.repositories.CoursRepository;
import org.ohara.restApi.repositories.SessionCoursRepository;
import org.ohara.restApi.services.SessionCoursService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SessionCoursServiceImpl implements SessionCoursService {
    private final SessionCoursRepository sessionCoursRepository;
    private final CoursRepository coursRepository;
    @Override
    public Page<SessionCours> getSessionCours(String module, Long professeurId, Pageable page) {
        if (module != null && !module.isEmpty()) {
            return sessionCoursRepository.findByModule(module,page);
        } else if (module != null && !module.isEmpty() && professeurId != null) {
            return sessionCoursRepository.findByProfessorAndModule(page, professeurId, module);

        } else if (professeurId != null) {
            return sessionCoursRepository.findByProfessorForCurrentMonth(page, professeurId);
        }
        else {
            return sessionCoursRepository.findAllByIsActiveTrue(page);
        }
    }

    @Override
    public Page<SessionCours> getSessionsByProfessorForCurrentMonth(Pageable pageable, Long professorId, String module) {
        if (module != null && !module.isEmpty()) {
            return sessionCoursRepository.findByProfessorAndModule(pageable, professorId, module);
        } else {
            return sessionCoursRepository.findByProfessorForCurrentMonth(pageable,professorId);
        }
    }

    @Override
    public Page<SessionCours> getSessionsByProfessorOnly(Long professorId, Pageable page,String period) {
        return sessionCoursRepository.findByProfessorForCurrentMonth(page, professorId);
    }

    @Override
    public long getTotalHoursByProfessorForMonth(Long professorId, String module, YearMonth month) {
        int monthValue = month.getMonthValue();
        int yearValue = month.getYear();

        List<SessionCours> sessions = sessionCoursRepository.findByProfessorIdAndMonth(professorId, monthValue, yearValue);

        // Filtrer les sessions par module si nécessaire
        if (module != null && !module.isEmpty()) {
            sessions = sessions.stream()
                    .filter(session -> session.getCours().getModule().equals(module))
                    .collect(Collectors.toList());
        }

        // Calculer le nombre total d'heures
        long totalHours = sessions.stream()
                .mapToLong(SessionCours::getNombreHeurePlanifier)
                .sum();

        return totalHours;
    }

    @Override
    public Page<SessionCours> getSessionCoursByEtatValid(Pageable page) {;
        return sessionCoursRepository.findByEtatSessionValider(page,EEtatSession.VALIDER);
    }

    @Override
    public Page<SessionCoursEtudiant> getEtudiantsBySessionCoursAndClasse(Long sessionId, String libelle, Pageable pageable) {
        return sessionCoursRepository.findBySessionCoursIdAndClasseLibelle(sessionId, libelle, pageable);

        /*SessionCours sessionCours = sessionCoursRepository.findById(sessionId)
                .orElseThrow(() -> new NoSuchElementException("SessionCours not found with id: " + sessionId));

        List<SessionCoursEtudiant> sessionCoursEtudiants;
        if (classeId != null) {
            sessionCoursEtudiants = sessionCours.getSessionCoursEtudiants().stream()
                    .filter(scEtudiant -> scEtudiant.getClasse().getId().equals(classeId))
                    .collect(Collectors.toList());

        } else {
            sessionCoursEtudiants = sessionCours.getSessionCoursEtudiants();
        }

        int start = (int) pageable.getOffset();
        //(start + pageable.getPageSize()) > sessionCoursEtudiants.size() ? sessionCoursEtudiants.size() : (start + pageable.getPageSize())
        int end = Math.min((start + pageable.getPageSize()), sessionCoursEtudiants.size());
        List<SessionCoursEtudiant> pageContent = sessionCoursEtudiants.subList(start, end);
        return new PageImpl<>(pageContent, pageable, sessionCoursEtudiants.size());*/

    }

    @Override
    public void addSessionCours(SessionCoursRequestDto dto, Long coursId) {
        SessionCours sessionCours = dto.TransformToEntity();
        sessionCours.setTypeSession(sessionCours.getTypeSession());
        sessionCours.setEtatSession(sessionCours.getEtatSession());

        sessionCours.setHeureDebut(sessionCours.getHeureDebut());
        sessionCours.setHeureFin(sessionCours.getHeureFin());
        sessionCours.setDate(sessionCours.getDate());
        sessionCours.setIsActive(true);

        Cours cours = coursRepository.findByIdAndIsActiveTrue(coursId);
        LocalTime heureDebut = sessionCours.getHeureDebut();
        LocalTime heureFin = sessionCours.getHeureFin();
        long nombreHeure = calculateHeurePlanifier(heureDebut, heureFin);

        if (ETypeSession.PRESENTIEL == sessionCours.getTypeSession()) {
            sessionCours.setSalle(dto.getSalle());
        } else {
            sessionCours.setSalle(null);
            sessionCours.setTypeSession(ETypeSession.PRESENTIEL);
        }
        sessionCours.setCours(cours);
        sessionCours.setNombreHeurePlanifier(nombreHeure);
        sessionCoursRepository.save(sessionCours);


    }
    private long calculateHeurePlanifier(LocalTime heureDebut, LocalTime heureFin) {
        Duration duration = Duration.between(heureDebut, heureFin);
        return duration.toHours();
    }


}
