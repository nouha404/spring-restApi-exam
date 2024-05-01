package org.ohara.restApi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.YearMonth;
import java.util.Map;

public interface SessionCoursRestController {

    @GetMapping("")
    ResponseEntity<?> listeSessionCours(
            @RequestParam(defaultValue = "0",name = "page") int page,
            @RequestParam(defaultValue = "5", name = "size") int size,
            @RequestParam(name = "professeurId",required = false) Long professeurId,
            @RequestParam(defaultValue = "", name = "module") String module
    );

    @GetMapping("/professeur/{professeurId}")
    ResponseEntity<?> listSessionsByProfessorForCurrentMonth(
            @PathVariable Long professeurId,
            @RequestParam(defaultValue = "0",name = "page") int page,
            @RequestParam(defaultValue = "5", name = "size") int size,
            @RequestParam(value = "period", required = false) String period
    );

    @GetMapping("/professeur/{professeurId}/total-hours")
    ResponseEntity<Map<Object,Object>> getTotalHoursByProfessor(
            @PathVariable Long professeurId,
            @RequestParam(required = false) String module,
            @RequestParam(required = false) String month
    );


}
