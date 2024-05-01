package org.ohara.restApi.controllers.impl;

import lombok.RequiredArgsConstructor;
import org.ohara.maVraiDep.data.entitties.Professeur;
import org.ohara.maVraiDep.data.entitties.SessionCours;
import org.ohara.maVraiDep.data.entitties.SessionCoursEtudiant;
import org.ohara.maVraiDep.data.web.dto.response.SessionCoursResponseDto;
import org.ohara.restApi.controllers.SessionCoursRestController;
import org.ohara.restApi.controllers.dto.response.RestResponseDto;
import org.ohara.restApi.services.SessionCoursService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sessions")
public class SessionCoursRestControllerImpl implements SessionCoursRestController {
    private final SessionCoursService sessionCoursService;
    @Override
    public ResponseEntity<Map<Object,Object>> listeSessionCours(int page, int size, Long professeurId, String module) {
        Page<SessionCours> sessionCours = sessionCoursService.getSessionCours(module,professeurId, PageRequest.of(page,size));


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
    public ResponseEntity<Map<Object,Object>> listSessionsByProfessorForCurrentMonth(Long professeurId,int page, int size, String period) {
        Page<SessionCours> sessions = sessionCoursService.getSessionsByProfessorOnly(professeurId, PageRequest.of(page,size),period);
        Page<SessionCoursResponseDto> sessionCoursDto = sessions.map(SessionCoursResponseDto::toDto);

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
    public ResponseEntity<Map<Object, Object>> getTotalHoursByProfessor(Long professeurId, String module, String month) {
        YearMonth yearMonth = YearMonth.parse(month);
        long totalHours = sessionCoursService.getTotalHoursByProfessorForMonth(professeurId, module, yearMonth);

        Map<Object,Object> response = RestResponseDto.response(
                totalHours,
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
