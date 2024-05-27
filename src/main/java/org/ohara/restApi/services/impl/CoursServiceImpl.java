package org.ohara.restApi.services.impl;

import lombok.RequiredArgsConstructor;
import org.ohara.maVraiDep.data.entitties.*;
import org.ohara.maVraiDep.data.entitties.Module;
import org.ohara.maVraiDep.data.enums.EtatCours;

import org.ohara.maVraiDep.data.web.dto.request.CoursRequestDto;
import org.ohara.restApi.repositories.*;
import org.ohara.restApi.services.CoursService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CoursServiceImpl implements CoursService {
    private final CoursRepository coursRepository;
    private final ProfesseurRepository professeurRepository;
    private final ModuleRepository moduleRepository;
    private final AnneeScolaireRepository anneeScolaireRepository;
    private final SemestreRepository semestreRepository;

    @Override
    public Page<Cours> getCoursByProfesseur(Long id, Pageable page) {
        Professeur professeur = professeurRepository.findById(id).orElse(null);
        return coursRepository.findByProfesseur(professeur,page);
    }

    @Override
    public Page<Cours> getCours(String etatCours,Long id, Pageable page) {
        if (etatCours != null && !etatCours.isEmpty()) {
            return coursRepository.findAllByEtatCoursAndIsActiveTrue(EtatCours.valueOf(etatCours), page);
        } else if (id!=null) {
            return coursRepository.findById(id,page);
        } else {
            return coursRepository.findAllByIsActiveTrue(page);
        }
    }

    @Override
    public List<Cours> getCours() {
        return coursRepository.findAllByIsActiveTrue();
    }

    @Override
    public Cours getCoursById(Long id) {
        return coursRepository.findByIdAndIsActiveTrue(id);
    }

    @Override
    public void addCours(CoursRequestDto dto) {

        try {

            Cours cours = dto.TransformToEntity();
            AnneeScolaire anneeScolaire = anneeScolaireRepository.findByIsActiveTrue();
            Module module;

            if (cours.getModule() != null) {
                if (cours.getModule().getId() != null) {
                    module = moduleRepository.findById(cours.getModule().getId())
                            .orElseThrow(() -> new IllegalArgumentException("Module not found with ID: " + cours.getModule().getId()));
                } else if (cours.getModule().getLibelle() != null) {
                    module = moduleRepository.findModuleByLibelle(cours.getModule().getLibelle())
                            .orElseGet(() -> {
                                Module newModule = new Module();
                                newModule.setLibelle(cours.getModule().getLibelle());
                                newModule.setIsActive(true);
                                return moduleRepository.save(newModule);
                            });
                } else {
                    throw new IllegalArgumentException("Module ID or Libelle is required.");
                }
            } else {
                throw new IllegalArgumentException("Module is required.");
            }


            Professeur professeur;
            if (cours.getProfesseur() != null) {
                if (cours.getProfesseur().getId() != null) {
                    professeur = professeurRepository.findById(cours.getProfesseur().getId())
                            .orElseThrow(() -> new IllegalArgumentException("Professeur not found with ID: " + cours.getProfesseur().getId()));
                } else if (cours.getProfesseur().getNom() != null && cours.getProfesseur().getPrenom() != null) {
                    professeur = professeurRepository.findByNomAndPrenom(cours.getProfesseur().getNom(), cours.getProfesseur().getPrenom())
                            .orElseGet(() -> {
                                Professeur newProfesseur = new Professeur();
                                newProfesseur.setNom(cours.getProfesseur().getNom());
                                newProfesseur.setPrenom(cours.getProfesseur().getPrenom());
                                newProfesseur.setGrade(cours.getProfesseur().getGrade());
                                newProfesseur.setIsActive(true);
                                newProfesseur.setSpecialite(cours.getProfesseur().getSpecialite());
                                return professeurRepository.save(newProfesseur);
                            });
                } else {
                    throw new IllegalArgumentException("Professeur ID or Nom/Prenom is required.");
                }
            } else {
                throw new IllegalArgumentException("Professeur is required.");
            }

            Semestre semestre;
            if (cours.getSemestre() != null) {
                if (cours.getSemestre().getId() != null) {
                    semestre = semestreRepository.findById(cours.getSemestre().getId())
                            .orElseThrow(() -> new IllegalArgumentException("Semestre not found with ID: " + cours.getSemestre().getId()));
                } else if (cours.getSemestre().getLibelle() != null) {
                    semestre = semestreRepository.findByLibelle(cours.getSemestre().getLibelle())
                            .orElseGet(() -> {
                                Semestre newSemestre = new Semestre();
                                newSemestre.setLibelle(cours.getSemestre().getLibelle());
                                newSemestre.setIsActive(true);
                                newSemestre.getNiveau().setLibelle(cours.getSemestre().getNiveau().getLibelle());
                                return semestreRepository.save(newSemestre);
                            });
                } else {
                    throw new IllegalArgumentException("Semestre ID or Libelle is required.");
                }
            } else {
                throw new IllegalArgumentException("Semestre is required.");
            }


            cours.setAnneeScolaire(anneeScolaire);
            cours.setProfesseur(professeur);
            cours.setModule(module);
            cours.setSemestre(semestre);
            cours.setNombreHeurePlanifier(cours.getNombreHeurePlanifier());
            cours.setHeuresEffectuees(LocalTime.ofSecondOfDay(0));
            cours.setEtatCours(EtatCours.EN_COURS);
            cours.setIsActive(true);
            cours.setNbreHeureGlobal(dto.getNbreHeureGlobal());
            coursRepository.save(cours);
        } catch (Exception e) {
            // Gérer toute exception ici et laisser le contrôleur gérer la réponse
            throw new RuntimeException("Erreur lors de l'ajout du cours: " + e.getMessage(), e);
        }

    }

}
