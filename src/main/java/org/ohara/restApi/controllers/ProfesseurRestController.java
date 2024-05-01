package org.ohara.restApi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


public interface ProfesseurRestController {
    @GetMapping("/professeurs")
    ResponseEntity<?> listerProfesseur(
            @RequestParam(defaultValue = "0",name = "page") int page,
            @RequestParam(defaultValue = "8",name = "size") int size
    );
    //Map<Object,Object>

}
