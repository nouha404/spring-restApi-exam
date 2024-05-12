package org.ohara.restApi.controllers.impl;

import lombok.RequiredArgsConstructor;
import org.ohara.maVraiDep.data.entitties.Cours;
import org.ohara.maVraiDep.data.entitties.SessionCours;
import org.ohara.maVraiDep.data.entitties.SessionCoursEtudiant;

import org.ohara.maVraiDep.data.web.dto.request.*;
import org.ohara.maVraiDep.data.web.dto.response.CoursResponseDto;
import org.ohara.maVraiDep.data.web.dto.response.SessionCoursEtudiantResponseDto;
import org.ohara.maVraiDep.data.web.dto.response.SessionCoursResponseDto;
import org.ohara.restApi.controllers.RpRestController;
import org.ohara.restApi.controllers.dto.response.RestResponseDto;
import org.ohara.restApi.repositories.SalleRepository;
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

    @Override
    public ResponseEntity<Map<Object,Object>> listeCours(int page, int size, String etat) {

        Page<Cours> cours = coursService.getCours(etat, PageRequest.of(page,size));
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