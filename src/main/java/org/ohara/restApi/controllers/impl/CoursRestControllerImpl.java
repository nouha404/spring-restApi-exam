package org.ohara.restApi.controllers.impl;

import lombok.RequiredArgsConstructor;
import org.ohara.maVraiDep.data.entitties.Cours;

import org.ohara.maVraiDep.data.web.dto.response.CoursResponseDto;
import org.ohara.restApi.controllers.CoursRestController;
import org.ohara.restApi.controllers.dto.response.RestResponseDto;

import org.ohara.restApi.services.CoursService;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cours")
public class CoursRestControllerImpl implements CoursRestController {
    private final CoursService coursService;

    @Override
    public ResponseEntity<Map<Object,Object>> listerCoursByPRofesseur(Long professeurId, int page, int size) {
        Page<Cours> cours = coursService.getCoursByProfesseur(professeurId, PageRequest.of(page,size));
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


}
