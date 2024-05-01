package org.ohara.restApi.controllers;

import jakarta.validation.Valid;
import org.ohara.maVraiDep.data.web.dto.request.AnneeScolaireRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

public interface AnneeScolaireRestController {

    @PostMapping("/anneeScolaires")
    ResponseEntity<?> createAnneeScolaire(@Valid @RequestBody AnneeScolaireRequestDto anneeScolaireDto,
                                          BindingResult bindingResult);

    @GetMapping("/anneeScolaires/{libelle}/libelle")
    ResponseEntity<?> getAnneScolaireByLibelle(
            @PathVariable String libelle
    );

    @PutMapping("/anneeScolaires/{libelle}/libelle")
    ResponseEntity<Map<Object,Object>> updateAnneeScolaire(
            @PathVariable String libelle
    );

}
