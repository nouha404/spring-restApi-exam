package org.ohara.restApi.security.data.reporitories;



import org.ohara.maVraiDep.data.security.data.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<AppRole,Long> {
    AppRole getByRoleName(String roleName);
}
