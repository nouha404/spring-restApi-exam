package org.ohara.restApi.security.data.fixtures;

import lombok.RequiredArgsConstructor;
import org.ohara.restApi.security.services.SecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//@Component
@RequiredArgsConstructor
@Order(1)
public class AppRoleFixtures implements CommandLineRunner {
   private final SecurityService securityService;
    @Override
    public void run(String... args) throws Exception {
          securityService.saveRole("RP");
          securityService.saveRole("PROFESSEUR");

    }
}
