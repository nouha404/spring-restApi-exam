package org.ohara.restApi.controllers.impl;

import lombok.RequiredArgsConstructor;
import org.ohara.maVraiDep.data.entitties.*;

import org.ohara.maVraiDep.data.entitties.Module;
import org.ohara.maVraiDep.data.enums.ENiveau;
import org.ohara.maVraiDep.data.enums.Specialiter;
import org.ohara.maVraiDep.data.web.dto.request.*;
import org.ohara.maVraiDep.data.web.dto.response.ClasseSimpleResponseDto;
import org.ohara.maVraiDep.data.web.dto.response.CoursResponseDto;
import org.ohara.maVraiDep.data.web.dto.response.SessionCoursEtudiantResponseDto;
import org.ohara.maVraiDep.data.web.dto.response.SessionCoursResponseDto;
import org.ohara.restApi.controllers.RpRestController;
import org.ohara.restApi.controllers.dto.response.RestResponseDto;
import org.ohara.restApi.services.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rp")
public class RpRestControllerImpl implements RpRestController {
    private final CoursService coursService;
    private final SessionCoursService sessionCoursService;
    private final ClasseService classeService;
    private final SalleService salleService;
    private final SemestreService semestreService;
    private final ModuleService moduleService;
    private final ProfesseurService professeurService;

    @Override
    public ResponseEntity<Map<Object,Object>> listeCours(int page, int size, String etat,Long id) {

        Page<Cours> cours = coursService.getCours(etat,id ,PageRequest.of(page,size));
        Page<CoursResponseDto> coursDto = cours.map(CoursResponseDto::toDto);

        Map<Object,Object> response = RestResponseDto.response(
                coursDto.getContent(),
                new int[coursDto.getTotalPages()],
                coursDto.getNumber(),
                page > 0 ? page-1:page,
                page < coursDto.getTotalPages() - 1 ? page+1:page,
                coursDto.getTotalElements(),
                coursDto.getTotalPages(),
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getCoursById(Long id) {
        Cours cours = coursService.getCoursById(id);
        Map<Object,Object> response = RestResponseDto.response(
                cours,
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Map<Object,Object>> listeSessionCoursValid(int page, int size) {
        Page<SessionCours> sessionCours = sessionCoursService.getSessionCoursByEtatValid(PageRequest.of(page,size));
        Page<SessionCoursResponseDto> sessionCoursDto = sessionCours.map(SessionCoursResponseDto::toDto);

        Map<Object,Object> response = RestResponseDto.response(
                sessionCoursDto.getContent(),
                new int[sessionCoursDto.getTotalPages()],
                sessionCoursDto.getNumber(),
                page > 0 ? page-1:page,
                page < sessionCoursDto.getTotalPages() - 1 ? page+1:page,
                sessionCoursDto.getTotalElements(),
                sessionCoursDto.getTotalPages(),
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<Object,Object>> allClasse(int page, int size) {
        Page<Classe> classes = classeService.getAllClasse(PageRequest.of(page,size));
        Page<ClasseSimpleResponseDto> classeDto = classes.map(ClasseSimpleResponseDto::toDto);

        Map<Object,Object> response = RestResponseDto.response(
                classeDto.getContent(),
                new int[classeDto.getTotalPages()],
                classeDto.getNumber(),
                page > 0 ? page-1:page,
                page < classeDto.getTotalPages() - 1 ? page+1:page,
                classeDto.getTotalElements(),
                classeDto.getTotalPages(),
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<Object,Object>> allSemestre(int page, int size) {
        List<Semestre> semestres = semestreService.getAllSemestre();

        List<Map<String, Object>> semestreList = semestres.stream()
                .map(semestre -> {
                    Map<String, Object> semestreMap = new HashMap<>();
                    semestreMap.put("id", semestre.getId());
                    semestreMap.put("libelle", semestre.getLibelle());
                    return semestreMap;
                })
                .toList();

        Map<Object,Object> response = RestResponseDto.response(
                semestreList,
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<Object,Object>> allGrades(int page, int size) {
        List<ENiveau> grades = List.of(ENiveau.values());
        Map<Object,Object> response = RestResponseDto.response(
                grades,
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<Object,Object>> allSalles(int page, int size) {
        List<Salle> sallesList = salleService.getAllSalles();
        List<Map<String, Object>> salles = sallesList.stream()
                .map(salle -> {
                    Map<String, Object> semestreMap = new HashMap<>();
                    semestreMap.put("id", salle.getId());
                    semestreMap.put("libelle", salle.getNom());
                    return semestreMap;
                })
                .toList();

        Map<Object,Object> response = RestResponseDto.response(
                salles,
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> allModules(int page, int size) {
        List<Module> modules = moduleService.getModule();
        List<Map<String, Object>> modulesList = modules.stream()
                .map(module -> {
                    Map<String, Object> modulesMap = new HashMap<>();
                    modulesMap.put("id", module.getId());
                    modulesMap.put("libelle", module.getLibelle());
                    return modulesMap;
                })
                .toList();
        Map<Object,Object> response = RestResponseDto.response(
                modulesList,
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Map<Object,Object>> allSepecialiter(int page, int size) {
        List<Specialiter> specialiters = List.of(Specialiter.values());
        Map<Object,Object> response = RestResponseDto.response(
                specialiters,
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Map<Object,Object>> addSessionCours(Long coursId, SessionCoursRequestDto sessionCoursDto, BindingResult bindingResult) {
        Map<Object, Object> response;
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            fieldErrors.forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));

            response = RestResponseDto.response(errors, HttpStatus.NOT_FOUND);

        } else {
            sessionCoursService.addSessionCours(sessionCoursDto,coursId);
            response = RestResponseDto.response(sessionCoursDto,HttpStatus.CREATED); //201
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Map<Object,Object>> listerEtudiantByCours(Long sessionId, int page, int size,String libelle) {

        Page<SessionCoursEtudiant> etudiants = sessionCoursService.getEtudiantsBySessionCoursAndClasse(sessionId,libelle,PageRequest.of(page,size));
        Page<SessionCoursEtudiantResponseDto> etudiantDto = etudiants.map(SessionCoursEtudiantResponseDto::toDto);
        Map<Object,Object> response = RestResponseDto.response(
                etudiantDto.getContent(),
                new int[etudiantDto.getTotalPages()],
                etudiantDto.getNumber(),
                page > 0 ? page-1:page,
                page < etudiantDto.getTotalPages() - 1 ? page+1:page,
                etudiantDto.getTotalElements(),
                etudiantDto.getTotalPages(),
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getProfesseurs(int page, int size) {

        Page<Professeur> professeurs = professeurService.getAllProfesseurs(PageRequest.of(page,size));
        /*List<String> nomsComplets = professeurs.getContent().stream()
                .map(professeur -> professeur.getPrenom() + " " + professeur.getNom())
                .collect(Collectors.toList());*/

        List<Map<String, Object>> professeursList = professeurs.getContent().stream()
                .map(professeur -> {
                    Map<String, Object> professeurMap = new HashMap<>();
                    professeurMap.put("id", professeur.getId());
                    professeurMap.put("nomComplet", professeur.getPrenom() + " " + professeur.getNom());
                    return professeurMap;
                })
                .collect(Collectors.toList());

        Map<Object,Object> response = RestResponseDto.response(
                professeursList,
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<Object,Object>> addCours(CoursRequestDto coursDto, BindingResult bindingResult) {
        Map<Object, Object> response;
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            fieldErrors.forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));

            response = RestResponseDto.response(errors, HttpStatus.NOT_FOUND);

        } else {
            coursService.addCours(coursDto);
            response = RestResponseDto.response(coursDto,HttpStatus.CREATED); //201
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<Object,Object>> cancelSessionCours(Long sessionId) {
        Map<Object, Object> response;
            sessionCoursService.cancelSessionCours(sessionId);
            response = RestResponseDto.response("Session de cours annulée avec succès",HttpStatus.CREATED); //201

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<Object,Object>> addClasse(ClasseRequestDto classeDto, BindingResult bindingResult) {
        Map<Object, Object> response;
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            fieldErrors.forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));

            response = RestResponseDto.response(errors, HttpStatus.NOT_FOUND);

        } else {
            classeService.addClasse(classeDto);
            response = RestResponseDto.response(classeDto,HttpStatus.CREATED); //201
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<Object,Object>> addSalle(SalleRequestDto salleDto, BindingResult bindingResult) {
        Map<Object, Object> response;
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            fieldErrors.forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));

            response = RestResponseDto.response(errors, HttpStatus.NOT_FOUND);

        } else {
            salleService.addSalle(salleDto);
            response = RestResponseDto.response(salleDto,HttpStatus.CREATED); //201
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> addProfesseur(ProfesseurSimpleResquestDto profDto, BindingResult bindingResult) {
        Map<Object, Object> response;
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            fieldErrors.forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));

            response = RestResponseDto.response(errors, HttpStatus.NOT_FOUND);

        } else {
            professeurService.addProfesseur(profDto);
            response = RestResponseDto.response(profDto,HttpStatus.CREATED); //201
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<Object,Object>> addSemestre(SemestreRequestDto semestreDto, BindingResult bindingResult) {
        Map<Object, Object> response;
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            fieldErrors.forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));

            response = RestResponseDto.response(errors, HttpStatus.NOT_FOUND);

        } else {
            semestreService.addSemestre(semestreDto);
            response = RestResponseDto.response(semestreDto,HttpStatus.CREATED); //201
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<Object,Object>> addModules(ModuleRequestDto moduleDto, BindingResult bindingResult) {
        Map<Object, Object> response;
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            fieldErrors.forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));

            response = RestResponseDto.response(errors, HttpStatus.NOT_FOUND);

        } else {
            moduleService.addModule(moduleDto);
            response = RestResponseDto.response(moduleDto,HttpStatus.CREATED); //201
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}