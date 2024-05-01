package org.ohara.restApi.controllers.impl;

import lombok.RequiredArgsConstructor;
import org.ohara.maVraiDep.data.entitties.Professeur;
import org.ohara.maVraiDep.data.web.dto.response.CoursResponseDto;
import org.ohara.maVraiDep.data.web.dto.response.ProfesseurSimpleResponseDto;
import org.ohara.restApi.controllers.ProfesseurRestController;
import org.ohara.restApi.controllers.dto.response.RestResponseDto;
import org.ohara.restApi.services.ProfesseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProfesseurRestControllerImpl implements ProfesseurRestController {
    private final ProfesseurService professeurService;


    @Override
    public ResponseEntity<Map<Object,Object>> listerProfesseur(int page, int size) {
        Page<Professeur> professeurPage =  professeurService.getProfesseur(PageRequest.of(page,size));
        Page<ProfesseurSimpleResponseDto> profeseurs = professeurPage.map(ProfesseurSimpleResponseDto::toDto);

        Map<Object,Object> response = RestResponseDto.response(
                profeseurs.getContent(),
                new int[profeseurs.getTotalPages()],
                profeseurs.getNumber(),
                page > 0 ? page-1:page,
                page < profeseurs.getTotalPages() - 1 ? page+1:page,
                profeseurs.getTotalElements(),
                profeseurs.getTotalPages(),
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
