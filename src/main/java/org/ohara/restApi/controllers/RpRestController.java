package org.ohara.restApi.controllers;

import jakarta.validation.Valid;
import org.ohara.maVraiDep.data.web.dto.request.*;
import org.ohara.maVraiDep.data.web.dto.response.CoursResponseDto;
import org.ohara.maVraiDep.data.web.dto.response.ProfesseurSimpleResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

public interface RpRestController {
    @GetMapping("/cours")
    ResponseEntity<?> listeCours(
            @RequestParam(defaultValue = "0",name = "page") int page,
            @RequestParam(defaultValue = "5", name = "size") int size,
            @RequestParam(required = false, name = "etat") String etat,
            @RequestParam(required = false, name = "id") Long id
    );

    @GetMapping("/cours/{id}")
    ResponseEntity<?> getCoursById(@PathVariable Long id);

    @GetMapping("/sessions")
    ResponseEntity<?> listeSessionCoursValid(
            @RequestParam(defaultValue = "0",name = "page") int page,
            @RequestParam(defaultValue = "5", name = "size") int size
    );
    @GetMapping("/classes")
    ResponseEntity<?> allClasse(
            @RequestParam(defaultValue = "0",name = "page") int page,
            @RequestParam(defaultValue = "5", name = "size") int size
    );
    @GetMapping("/semestres")
    ResponseEntity<?> allSemestre(
            @RequestParam(defaultValue = "0",name = "page") int page,
            @RequestParam(defaultValue = "5", name = "size") int size
    );
    @GetMapping("/professeurs")
    ResponseEntity<?> getProfesseurs(@RequestParam(defaultValue = "0",name = "page") int page,
                                     @RequestParam(defaultValue = "5", name = "size") int size);

    @GetMapping("/grades")
    ResponseEntity<?> allGrades(
            @RequestParam(defaultValue = "0",name = "page") int page,
            @RequestParam(defaultValue = "5", name = "size") int size
    );

    @GetMapping("/salles")
    ResponseEntity<?> allSalles(
            @RequestParam(defaultValue = "0",name = "page") int page,
            @RequestParam(defaultValue = "5", name = "size") int size
    );
    @GetMapping("/modules")
    ResponseEntity<?> allModules(
            @RequestParam(defaultValue = "0",name = "page") int page,
            @RequestParam(defaultValue = "5", name = "size") int size
    );

    @GetMapping("/spec")
    ResponseEntity<?> allSepecialiter(
            @RequestParam(defaultValue = "0",name = "page") int page,
            @RequestParam(defaultValue = "5", name = "size") int size
    );

    @PostMapping("/sessions/{coursId}")
    ResponseEntity<?> addSessionCours(
            @PathVariable Long coursId,
            @Valid @RequestBody  SessionCoursRequestDto sessionCoursDto,
            BindingResult bindingResult);


    @GetMapping("/session/{sessionId}/etudiant")
    ResponseEntity<?> listerEtudiantByCours(
            @PathVariable Long sessionId,
            @RequestParam(defaultValue = "0",name = "page") int page,
            @RequestParam(defaultValue = "5", name = "size") int size,
            @RequestParam(required = false) String libelle
    );


    @PostMapping("/cours")
    ResponseEntity<?> addCours(@Valid @RequestBody CoursRequestDto coursDto,
                               BindingResult bindingResult);

    @DeleteMapping("/session/{sessionId}/cancel")
    ResponseEntity<?> cancelSessionCours(
            @PathVariable Long sessionId
    );
    @PostMapping("/classes")
    ResponseEntity<?> addClasse(@Valid @RequestBody ClasseRequestDto classeDto,
                               BindingResult bindingResult);

    @PostMapping("/salles")
    ResponseEntity<?> addSalle(@Valid @RequestBody SalleRequestDto salleDto,
                                BindingResult bindingResult);

    @PostMapping("/professeurs")
    ResponseEntity<?> addProfesseur(@Valid @RequestBody ProfesseurSimpleResquestDto profDto,
                               BindingResult bindingResult);

    @PostMapping("/semestres")
    ResponseEntity<?> addSemestre(@Valid @RequestBody SemestreRequestDto semestreDto,
                               BindingResult bindingResult);

    @PostMapping("/modules")
    ResponseEntity<?> addModules(@Valid @RequestBody ModuleRequestDto moduleDto,
                                  BindingResult bindingResult);

}
