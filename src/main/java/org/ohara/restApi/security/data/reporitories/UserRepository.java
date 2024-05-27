package org.ohara.restApi.security.data.reporitories;

import org.ohara.maVraiDep.data.security.data.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser,Long> {
      AppUser findByUsername(String username);
}
