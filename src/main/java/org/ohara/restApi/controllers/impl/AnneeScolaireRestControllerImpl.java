package org.ohara.restApi.controllers.impl;

import lombok.RequiredArgsConstructor;
import org.ohara.maVraiDep.data.entitties.AnneeScolaire;
import org.ohara.maVraiDep.data.entitties.SessionCoursEtudiant;
import org.ohara.maVraiDep.data.web.dto.request.AnneeScolaireRequestDto;
import org.ohara.maVraiDep.data.web.dto.response.SessionCoursEtudiantResponseDto;
import org.ohara.restApi.controllers.AnneeScolaireRestController;
import org.ohara.restApi.controllers.dto.response.RestResponseDto;
import org.ohara.restApi.services.AnneeScolaireService;
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
public class AnneeScolaireRestControllerImpl implements AnneeScolaireRestController {
    private  final AnneeScolaireService anneeScolaireService;
    @Override
    public ResponseEntity<Map<Object,Object>> createAnneeScolaire(AnneeScolaireRequestDto anneeScolaireDto, BindingResult bindingResult) {

        Map<Object, Object> response;
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            fieldErrors.forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));

            response = RestResponseDto.response(errors, HttpStatus.NOT_FOUND);

        } else {
            anneeScolaireService.createAnneeScolaire(anneeScolaireDto);
            response = RestResponseDto.response(anneeScolaireDto,HttpStatus.CREATED); //201
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<Object,Object>> getAnneScolaireByLibelle(String libelle) {
        AnneeScolaire anneeScolaire = anneeScolaireService.getAnneScolaireByLibelle(libelle);
        Map<Object,Object> response = RestResponseDto.response(
                anneeScolaire,
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<Object, Object>> updateAnneeScolaire(String libelle) {
        anneeScolaireService.updateAnneScolaireActived(libelle);
        return getAnneScolaireByLibelle(libelle);
    }
}
