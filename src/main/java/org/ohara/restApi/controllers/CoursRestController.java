package org.ohara.restApi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface CoursRestController {

    @GetMapping("/professeur/{professeurId}")
    ResponseEntity<?> listerCoursByPRofesseur(
            @PathVariable Long professeurId,
            @RequestParam(defaultValue = "0",name = "page") int page,
            @RequestParam(defaultValue = "8",name = "size") int size
    );


}
